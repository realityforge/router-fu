package router.fu.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;
import static javax.tools.Diagnostic.Kind.*;

/**
 * Annotation processor that analyzes Router annotated source and generates a router
 * implementation from the annotations.
 */
@SuppressWarnings( "Duplicates" )
@AutoService( Processor.class )
@SupportedAnnotationTypes( { "router.fu.annotations.*" } )
@SupportedSourceVersion( SourceVersion.RELEASE_8 )
public final class RouterProcessor
  extends AbstractProcessor
{
  private static final Pattern CALLBACK_PATTERN = Pattern.compile( "^([a-z].*)Callback$" );

  private final Pattern _urlParameterPattern = Pattern.compile( "^:([a-zA-Z0-9\\-_]*[a-zA-Z0-9])(<(.+?)>)?" );
  private final Pattern _separatorPattern = Pattern.compile( "^([!&\\-/_.;])" );
  private final Pattern _fragmentPattern = Pattern.compile( "^([0-9a-zA-Z]+)" );

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean process( final Set<? extends TypeElement> annotations, final RoundEnvironment env )
  {
    final Set<? extends Element> elements = env.getElementsAnnotatedWith( Router.class );
    processElements( elements );
    return false;
  }

  private void processElements( @Nonnull final Set<? extends Element> elements )
  {
    for ( final Element element : elements )
    {
      try
      {
        process( (TypeElement) element );
      }
      catch ( final IOException ioe )
      {
        processingEnv.getMessager().printMessage( ERROR, ioe.getMessage(), element );
      }
      catch ( final RouterProcessorException e )
      {
        processingEnv.getMessager().printMessage( ERROR, e.getMessage(), e.getElement() );
      }
      catch ( final Throwable e )
      {
        final StringWriter sw = new StringWriter();
        e.printStackTrace( new PrintWriter( sw ) );
        sw.flush();

        final String message =
          "Unexpected error will running the " + getClass().getName() + " processor. This has " +
          "resulted in a failure to process the code and has left the compiler in an invalid " +
          "state. Please report the failure to the developers so that it can be fixed.\n" +
          " Report the error at: https://github.com/realityforge/router-fu/issues\n" +
          "\n\n" +
          sw.toString();
        processingEnv.getMessager().printMessage( ERROR, message, element );
      }
    }
  }

  private void process( @Nonnull final TypeElement element )
    throws IOException, RouterProcessorException
  {
    final RouterDescriptor descriptor = parse( element );
    emitTypeSpec( descriptor.getPackageName(), Generator.buildService( descriptor ) );
    emitTypeSpec( descriptor.getPackageName(), Generator.buildRouterImpl( descriptor ) );
  }

  private void emitTypeSpec( @Nonnull final String packageName, @Nonnull final TypeSpec typeSpec )
    throws IOException
  {
    JavaFile.builder( packageName, typeSpec ).
      skipJavaLangImports( true ).
      build().
      writeTo( processingEnv.getFiler() );
  }

  @Nonnull
  private RouterDescriptor parse( @Nonnull final TypeElement typeElement )
  {
    final Router component = typeElement.getAnnotation( Router.class );
    assert null != component;
    final PackageElement packageElement = processingEnv.getElementUtils().getPackageOf( typeElement );
    final RouterDescriptor descriptor = new RouterDescriptor( packageElement, typeElement );
    descriptor.setArezComponent( component.arez() );

    parseRouteAnnotations( typeElement, descriptor );
    parseBoundParameterAnnotations( typeElement, descriptor );

    parseRouteCallbacks( typeElement, descriptor );

    parseRouterRefs( descriptor, typeElement );

    return descriptor;
  }

  private void parseRouterRefs( @Nonnull final RouterDescriptor descriptor, @Nonnull final TypeElement typeElement )
  {
    final List<ExecutableElement> methods =
      ProcessorUtil.getMethods( typeElement, processingEnv.getTypeUtils() ).stream().
        filter( m -> null != m.getAnnotation( RouterRef.class ) ).collect( Collectors.toList() );

    final ArrayList<ExecutableElement> routerRefMethods = new ArrayList<>();
    for ( final ExecutableElement method : methods )
    {
      ProcessorUtil.mustBeOverridable( RouterRef.class, method );
      ProcessorUtil.mustNotHaveAnyParameters( RouterRef.class, method );
      ProcessorUtil.mustNotThrowAnyExceptions( RouterRef.class, method );
      final String expectedServiceName = descriptor.getServiceClassName().toString();
      final TypeMirror returnType = method.getReturnType();

      if ( TypeKind.ERROR != returnType.getKind() )
      {
        if ( TypeKind.DECLARED != returnType.getKind() )
        {
          throw new RouterProcessorException( "Method annotated with @RouterRef must return an instance of " +
                                              expectedServiceName, method );
        }
        else
        {
          final TypeElement returnTypeElement = (TypeElement) processingEnv.getTypeUtils().asElement( returnType );
          if ( !returnTypeElement.getQualifiedName().toString().equals( expectedServiceName ) )
          {
            throw new RouterProcessorException( "Method annotated with @RouterRef must return an instance of " +
                                                expectedServiceName, method );
          }
        }
      }
      routerRefMethods.add( method );
    }

    descriptor.setRouterRefMethods( routerRefMethods );
  }

  private void parseRouteCallbacks( @Nonnull final TypeElement typeElement, @Nonnull final RouterDescriptor descriptor )
  {
    for ( final ExecutableElement method : ProcessorUtil.getMethods( typeElement, processingEnv.getTypeUtils() ) )
    {
      final RouteCallback annotation = method.getAnnotation( RouteCallback.class );
      if ( null != annotation )
      {
        parseRouteCallback( descriptor, method, annotation );
      }
    }
  }

  private void parseRouteCallback( @Nonnull final RouterDescriptor descriptor,
                                   @Nonnull final ExecutableElement method,
                                   @Nonnull final RouteCallback annotation )
  {
    final String name = deriveName( method, CALLBACK_PATTERN, annotation.name() );
    if ( null == name )
    {
      throw new RouterProcessorException( "@RouteCallback target has not specified a name and is not named " +
                                          "according to pattern '[Name]Callback'", method );
    }
    else if ( !descriptor.hasRouteNamed( name ) )
    {
      throw new RouterProcessorException( "@RouteCallback target has name '" + name + "' but no corresponding " +
                                          "route exists.", method );
    }
    else
    {
      final RouteDescriptor route = descriptor.getRouteByName( name );
      if ( route.hasCallback() )
      {
        throw new RouterProcessorException( "@RouteCallback target duplicates an existing route callback method " +
                                            "named '" + route.getCallback().getSimpleName().toString() +
                                            "'route exists.", method );
      }
      else
      {
        ProcessorUtil.mustBeSubclassCallable( RouteCallback.class, method );

        int locationIndex = -1;
        int routeIndex = -1;
        int parametersIndex = -1;

        final ExecutableType methodType = toMethodType( descriptor.getElement(), method );
        final List<? extends TypeMirror> parameterTypes = methodType.getParameterTypes();
        for ( int i = 0; i < parameterTypes.size(); i++ )
        {
          final TypeMirror paramType = parameterTypes.get( i );
          if ( TypeKind.DECLARED == paramType.getKind() )
          {
            final DeclaredType type = (DeclaredType) paramType;
            if ( type.toString().equals( "java.lang.String" ) )
            {
              if ( -1 == locationIndex )
              {
                locationIndex = i;
                continue;
              }
              else
              {
                throw duplicateCallbackParamException( method, "location", locationIndex, i );
              }
            }
            else if ( type.toString().equals( "router.fu.Route" ) )
            {
              if ( -1 == routeIndex )
              {
                routeIndex = i;
                continue;
              }
              else
              {
                throw duplicateCallbackParamException( method, "route", routeIndex, i );
              }
            }
            else if ( type.toString().equals( "java.util.Map<router.fu.Parameter,java.lang.String>" ) )
            {
              if ( -1 == parametersIndex )
              {
                parametersIndex = i;
                continue;
              }
              else
              {
                throw duplicateCallbackParamException( method, "parameters", parametersIndex, i );
              }
            }
          }

          final String paramName = method.getParameters().get( i ).getSimpleName().toString();
          throw new RouterProcessorException( "@RouteCallback target has unexpected parameter named '" +
                                              paramName + "' that does not an expected type. " +
                                              "Actual type: " + paramType, method );
        }

        route.setCallback( method, locationIndex, routeIndex, parametersIndex );
      }
    }
  }

  @Nonnull
  private RouterProcessorException duplicateCallbackParamException( @Nonnull final ExecutableElement method,
                                                                    @Nonnull final String paramTypeName,
                                                                    final int existingIndex,
                                                                    final int newIndex )
  {
    final String existingName = method.getParameters().get( existingIndex ).getSimpleName().toString();
    final String newName = method.getParameters().get( newIndex ).getSimpleName().toString();
    return new RouterProcessorException( "@RouteCallback target has two '" + paramTypeName +
                                         "' parameters named '" + existingName + "' and '" + newName + "'",
                                         method );
  }

  private ExecutableType toMethodType( @Nonnull final TypeElement element, @Nonnull final ExecutableElement method )
  {
    final DeclaredType classType = (DeclaredType) element.asType();
    return (ExecutableType) processingEnv.getTypeUtils().asMemberOf( classType, method );
  }

  private void parseBoundParameterAnnotations( @Nonnull final TypeElement typeElement,
                                               @Nonnull final RouterDescriptor descriptor )
  {
    Arrays.stream( typeElement.getAnnotationsByType( BoundParameter.class ) )
      .forEach( routeAnnotation -> parseBoundParameterAnnotation( typeElement, descriptor, routeAnnotation ) );
  }

  private void parseBoundParameterAnnotation( @Nonnull final TypeElement typeElement,
                                              @Nonnull final RouterDescriptor router,
                                              @Nonnull final BoundParameter annotation )
  {
    final String name = annotation.name();
    if ( !ProcessorUtil.isJavaIdentifier( name ) )
    {
      throw new RouterProcessorException( "@Router target has a @BoundParameter with an invalid name '" + name + "'",
                                          typeElement );
    }
    final String parameterName = annotation.parameterName().isEmpty() ? name : annotation.parameterName();

    final String[] routeNames = annotation.routeNames();

    final LinkedHashMap<RouteDescriptor, ParameterDescriptor> bindings = new LinkedHashMap<>();
    if ( 0 == routeNames.length )
    {
      for ( final RouteDescriptor route : router.getRoutes() )
      {
        for ( final ParameterDescriptor descriptor : route.getParameters() )
        {
          if ( descriptor.getName().equals( parameterName ) )
          {
            bindings.put( route, descriptor );
          }
        }
      }
      if ( bindings.isEmpty() )
      {
        throw new RouterProcessorException( "@Router target has a @BoundParameter that specifies a parameter " +
                                            "named '" + parameterName + "' but parameter does not exist on " +
                                            "any routes.", typeElement );
      }
    }
    else
    {
      for ( final String routeName : routeNames )
      {
        if ( router.hasRouteNamed( routeName ) )
        {
          final RouteDescriptor routeDescriptor = router.getRouteByName( routeName );
          final ParameterDescriptor parameterDescriptor = routeDescriptor.findParameterByName( parameterName );
          if ( null != parameterDescriptor )
          {
            bindings.put( routeDescriptor, parameterDescriptor );
          }
          else
          {
            throw new RouterProcessorException( "@Router target has a @BoundParameter that specifies a route named '" +
                                                routeName + "' for parameter named '" + parameterName +
                                                "' but parameter does not exist.", typeElement );
          }
        }
        else
        {
          throw new RouterProcessorException( "@Router target has a @BoundParameter that specifies a route named '" +
                                              routeName + "' that does not exist.", typeElement );
        }
      }

    }

    final BoundParameterDescriptor boundParameter = new BoundParameterDescriptor( name, bindings );
    if ( router.hasBoundParameterNamed( name ) )
    {
      throw new RouterProcessorException( "@Router target has multiple @BoundParameter annotations with the " +
                                          "name '" + name + "'", typeElement );
    }

    router.addBoundParameter( boundParameter );
  }

  private void parseRouteAnnotations( @Nonnull final TypeElement typeElement,
                                      @Nonnull final RouterDescriptor descriptor )
  {
    Arrays.stream( typeElement.getAnnotationsByType( Route.class ) )
      .forEach( routeAnnotation -> parseRouteAnnotation( typeElement, descriptor, routeAnnotation ) );
  }

  private void parseRouteAnnotation( @Nonnull final TypeElement typeElement,
                                     @Nonnull final RouterDescriptor router,
                                     @Nonnull final Route annotation )
  {
    final String name = annotation.name();
    if ( !ProcessorUtil.isJavaIdentifier( name ) )
    {
      throw new RouterProcessorException( "@Router target has a route with an invalid name '" + name + "'",
                                          typeElement );

    }
    final boolean navigationTarget = annotation.navigationTarget();
    final boolean partialMatch = annotation.partialMatch();
    final RouteDescriptor route = new RouteDescriptor( name, navigationTarget, partialMatch );

    if ( router.hasRouteNamed( name ) )
    {
      throw new RouterProcessorException( "@Router target has multiple routes with the name '" + name + "'",
                                          typeElement );
    }

    parseRoutePath( typeElement, route, annotation.path() );

    router.addRoute( route );
  }

  private void parseRoutePath( @Nonnull final TypeElement typeElement,
                               @Nonnull final RouteDescriptor route,
                               @Nonnull final String path )
  {
    final int length = path.length();
    int start = 0;

    while ( start < length )
    {
      // Match path parameters
      {
        final Matcher matcher = _urlParameterPattern.matcher( path.substring( start ) );
        if ( matcher.find() )
        {
          final String matched = matcher.group();
          start += matched.length();
          final String name = matcher.group( 1 );
          final String constraint = matcher.groupCount() > 1 ? matcher.group( 3 ) : null;
          route.addParameter( new ParameterDescriptor( name, constraint ) );
          continue;
        }
      }
      // Match separators
      {
        final Matcher matcher = _separatorPattern.matcher( path.substring( start ) );
        if ( matcher.find() )
        {
          final String matched = matcher.group();
          start += matched.length();
          route.addText( matched );
          continue;
        }
      }

      // Match fragments
      {
        final Matcher matcher = _fragmentPattern.matcher( path.substring( start ) );
        if ( matcher.find() )
        {
          final String matched = matcher.group();
          start += matched.length();
          route.addText( matched );
          continue;
        }
      }

      throw new RouterProcessorException( "@Route named '" + route.getName() + "' has a path that can not " +
                                          "be parsed: '" + path + "'",
                                          typeElement );
    }
  }

  @Nullable
  private String deriveName( @Nonnull final ExecutableElement method,
                             @Nonnull final Pattern pattern,
                             @Nonnull final String name )
    throws RouterProcessorException
  {
    if ( name.isEmpty() )
    {
      final String methodName = method.getSimpleName().toString();
      final Matcher matcher = pattern.matcher( methodName );
      if ( matcher.find() )
      {
        final String candidate = matcher.group( 1 );
        return Character.toLowerCase( candidate.charAt( 0 ) ) + candidate.substring( 1 );
      }
      else
      {
        return null;
      }
    }
    else
    {
      return name;
    }
  }
}
