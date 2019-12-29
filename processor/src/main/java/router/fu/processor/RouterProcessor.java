package router.fu.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.realityforge.proton.AbstractStandardProcessor;
import org.realityforge.proton.AnnotationsUtil;
import org.realityforge.proton.ElementsUtil;
import org.realityforge.proton.MemberChecks;
import org.realityforge.proton.ProcessorException;

/**
 * Annotation processor that analyzes Router annotated source and generates a router
 * implementation from the annotations.
 */
@SuppressWarnings( "Duplicates" )
@SupportedAnnotationTypes( Constants.ROUTER_ANNOTATION_CLASSNAME )
@SupportedSourceVersion( SourceVersion.RELEASE_8 )
@SupportedOptions( { "router.fu.defer.unresolved", "router.fu.defer.errors" } )
public final class RouterProcessor
  extends AbstractStandardProcessor
{
  private static final Pattern CALLBACK_PATTERN = Pattern.compile( "^([a-z].*)Callback$" );
  private final Pattern _urlParameterPattern = Pattern.compile( "^:([a-zA-Z0-9\\-_]*[a-zA-Z0-9])(<(.+?)>)?" );
  private final Pattern _separatorPattern = Pattern.compile( "^([!&\\-/_.;])" );
  private final Pattern _fragmentPattern = Pattern.compile( "^([0-9a-zA-Z]+)" );

  @SuppressWarnings( "unchecked" )
  @Nonnull
  @Override
  protected Set<TypeElement> getTypeElementsToProcess( @Nonnull final RoundEnvironment env )
  {
    final TypeElement annotation =
      processingEnv.getElementUtils().getTypeElement( Constants.ROUTER_ANNOTATION_CLASSNAME );
    return (Set<TypeElement>) env.getElementsAnnotatedWith( annotation );
  }

  @Nonnull
  @Override
  protected String getIssueTrackerURL()
  {
    return "https://github.com/realityforge/router-fu/issues";
  }

  @Nonnull
  @Override
  protected String getOptionPrefix()
  {
    return "router.fu";
  }

  @Override
  protected void process( @Nonnull final TypeElement element )
    throws IOException, ProcessorException
  {
    final RouterDescriptor descriptor = parse( element );
    emitTypeSpec( descriptor.getPackageName(), Generator.buildService( descriptor ) );
    emitTypeSpec( descriptor.getPackageName(), Generator.buildRouterImpl( descriptor ) );
  }

  @Override
  protected boolean shouldDeferUnresolved()
  {
    return false;
  }

  @Nonnull
  private RouterDescriptor parse( @Nonnull final TypeElement typeElement )
  {
    final AnnotationMirror annotation =
      AnnotationsUtil.getAnnotationByType( typeElement, Constants.ROUTER_ANNOTATION_CLASSNAME );
    final boolean arezComponent = AnnotationsUtil.getAnnotationValue( annotation, "arez" );
    final PackageElement packageElement = processingEnv.getElementUtils().getPackageOf( typeElement );
    final RouterDescriptor descriptor = new RouterDescriptor( packageElement, typeElement );
    descriptor.setArezComponent( arezComponent );

    parseRouteAnnotations( descriptor );
    parseBoundParameterAnnotations( descriptor );

    parseRouteCallbacks( descriptor );

    parseRouterRefs( descriptor );

    return descriptor;
  }

  private void parseRouterRefs( @Nonnull final RouterDescriptor descriptor )
  {
    final List<ExecutableElement> methods =
      getMethods( descriptor.getElement() ).stream().
        filter( m -> null != AnnotationsUtil.findAnnotationByType( m, Constants.ROUTER_REF_ANNOTATION_CLASSNAME ) ).
        collect( Collectors.toList() );

    final ArrayList<ExecutableElement> routerRefMethods = new ArrayList<>();
    for ( final ExecutableElement method : methods )
    {
      MemberChecks.mustBeOverridable( descriptor.getElement(),
                                      Constants.ROUTER_ANNOTATION_CLASSNAME,
                                      Constants.ROUTER_REF_ANNOTATION_CLASSNAME,
                                      method );
      MemberChecks.mustNotHaveAnyParameters( Constants.ROUTER_REF_ANNOTATION_CLASSNAME, method );
      MemberChecks.mustNotThrowAnyExceptions( Constants.ROUTER_REF_ANNOTATION_CLASSNAME, method );
      final String expectedServiceName = descriptor.getServiceClassName().toString();
      final TypeMirror returnType = method.getReturnType();

      if ( TypeKind.ERROR != returnType.getKind() )
      {
        if ( TypeKind.DECLARED != returnType.getKind() )
        {
          throw new ProcessorException( "Method annotated with @RouterRef must return an instance of " +
                                        expectedServiceName, method );
        }
        else
        {
          final TypeElement returnTypeElement = (TypeElement) processingEnv.getTypeUtils().asElement( returnType );
          if ( !returnTypeElement.getQualifiedName().toString().equals( expectedServiceName ) )
          {
            throw new ProcessorException( "Method annotated with @RouterRef must return an instance of " +
                                          expectedServiceName, method );
          }
        }
      }
      routerRefMethods.add( method );
    }

    descriptor.setRouterRefMethods( routerRefMethods );
  }

  private void parseRouteCallbacks( @Nonnull final RouterDescriptor descriptor )
  {
    for ( final ExecutableElement method : getMethods( descriptor.getElement() ) )
    {
      final AnnotationMirror annotation =
        AnnotationsUtil.findAnnotationByType( method, Constants.ROUTE_CALLBACK_ANNOTATION_CLASSNAME );
      if ( null != annotation )
      {
        parseRouteCallback( descriptor, method, annotation );
      }
    }
  }

  @Nonnull
  private List<ExecutableElement> getMethods( @Nonnull final TypeElement typeElement )
  {
    return ElementsUtil.getMethods( typeElement, processingEnv.getElementUtils(), processingEnv.getTypeUtils() );
  }

  private void parseRouteCallback( @Nonnull final RouterDescriptor descriptor,
                                   @Nonnull final ExecutableElement method,
                                   @Nonnull final AnnotationMirror annotation )
  {
    final String name = deriveCallbackName( method, AnnotationsUtil.getAnnotationValue( annotation, "name" ) );
    if ( null == name )
    {
      throw new ProcessorException( "@RouteCallback target has not specified a name and is not named " +
                                    "according to pattern '[Name]Callback'", method );
    }
    else if ( !descriptor.hasRouteNamed( name ) )
    {
      throw new ProcessorException( "@RouteCallback target has name '" + name + "' but no corresponding " +
                                    "route exists.", method );
    }
    else
    {
      final RouteDescriptor route = descriptor.getRouteByName( name );
      if ( route.hasCallback() )
      {
        throw new ProcessorException( "@RouteCallback target duplicates an existing route callback method " +
                                      "named '" + route.getCallback().getSimpleName().toString() +
                                      "'route exists.", method );
      }
      else
      {
        MemberChecks.mustBeOverridable( descriptor.getElement(),
                                        Constants.ROUTER_ANNOTATION_CLASSNAME,
                                        Constants.ROUTE_CALLBACK_ANNOTATION_CLASSNAME,
                                        method );

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
            switch ( type.toString() )
            {
              case "java.lang.String":
                if ( -1 == locationIndex )
                {
                  locationIndex = i;
                  continue;
                }
                else
                {
                  throw duplicateCallbackParamException( method, "location", locationIndex, i );
                }
              case "router.fu.Route":
                if ( -1 == routeIndex )
                {
                  routeIndex = i;
                  continue;
                }
                else
                {
                  throw duplicateCallbackParamException( method, "route", routeIndex, i );
                }
              case "java.util.Map<router.fu.Parameter,java.lang.String>":
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
          throw new ProcessorException( "@RouteCallback target has unexpected parameter named '" +
                                        paramName + "' that does not an expected type. " +
                                        "Actual type: " + paramType, method );
        }

        route.setCallback( method, methodType, locationIndex, routeIndex, parametersIndex );
      }
    }
  }

  @Nonnull
  private ProcessorException duplicateCallbackParamException( @Nonnull final ExecutableElement method,
                                                              @Nonnull final String paramTypeName,
                                                              final int existingIndex,
                                                              final int newIndex )
  {
    final String existingName = method.getParameters().get( existingIndex ).getSimpleName().toString();
    final String newName = method.getParameters().get( newIndex ).getSimpleName().toString();
    return new ProcessorException( "@RouteCallback target has two '" + paramTypeName +
                                   "' parameters named '" + existingName + "' and '" + newName + "'",
                                   method );
  }

  private ExecutableType toMethodType( @Nonnull final TypeElement element, @Nonnull final ExecutableElement method )
  {
    final DeclaredType classType = (DeclaredType) element.asType();
    return (ExecutableType) processingEnv.getTypeUtils().asMemberOf( classType, method );
  }

  private void parseBoundParameterAnnotations( @Nonnull final RouterDescriptor descriptor )
  {
    AnnotationsUtil.getRepeatingAnnotations( descriptor.getElement(),
                                             Constants.BOUND_PARAMETERS_ANNOTATION_CLASSNAME,
                                             Constants.BOUND_PARAMETER_ANNOTATION_CLASSNAME )
      .forEach( routeAnnotation -> parseBoundParameterAnnotation( descriptor.getElement(),
                                                                  descriptor,
                                                                  routeAnnotation ) );
  }

  private void parseBoundParameterAnnotation( @Nonnull final TypeElement typeElement,
                                              @Nonnull final RouterDescriptor router,
                                              @Nonnull final AnnotationMirror annotation )
  {
    final String name = AnnotationsUtil.getAnnotationValue( annotation, "name" );
    if ( !SourceVersion.isIdentifier( name ) )
    {
      throw new ProcessorException( "@Router target has a @BoundParameter with an invalid name '" + name + "'",
                                    typeElement );
    }
    final String declaredParameterName = AnnotationsUtil.getAnnotationValue( annotation, "parameterName" );
    final String parameterName = declaredParameterName.isEmpty() ? name : declaredParameterName;

    final List<AnnotationValue> routeNames =
      AnnotationsUtil.getAnnotationValue( annotation, "routeNames" );

    final LinkedHashMap<RouteDescriptor, ParameterDescriptor> bindings = new LinkedHashMap<>();
    if ( routeNames.isEmpty() )
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
        throw new ProcessorException( "@Router target has a @BoundParameter that specifies a parameter " +
                                      "named '" + parameterName + "' but parameter does not exist on " +
                                      "any routes.", typeElement );
      }
    }
    else
    {
      for ( final AnnotationValue route : routeNames )
      {
        final String routeName = (String) route.getValue();
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
            throw new ProcessorException( "@Router target has a @BoundParameter that specifies a route named '" +
                                          routeName + "' for parameter named '" + parameterName +
                                          "' but parameter does not exist.", typeElement );
          }
        }
        else
        {
          throw new ProcessorException( "@Router target has a @BoundParameter that specifies a route named '" +
                                        routeName + "' that does not exist.", typeElement );
        }
      }

    }

    final BoundParameterDescriptor boundParameter = new BoundParameterDescriptor( name, bindings );
    if ( router.hasBoundParameterNamed( name ) )
    {
      throw new ProcessorException( "@Router target has multiple @BoundParameter annotations with the " +
                                    "name '" + name + "'", typeElement );
    }

    router.addBoundParameter( boundParameter );
  }

  private void parseRouteAnnotations( @Nonnull final RouterDescriptor descriptor )
  {
    AnnotationsUtil.getRepeatingAnnotations( descriptor.getElement(),
                                             Constants.ROUTES_ANNOTATION_CLASSNAME,
                                             Constants.ROUTE_ANNOTATION_CLASSNAME )
      .forEach( routeAnnotation -> parseRouteAnnotation( descriptor, routeAnnotation ) );
  }

  private void parseRouteAnnotation( @Nonnull final RouterDescriptor descriptor,
                                     @Nonnull final AnnotationMirror annotation )
  {
    final String name = AnnotationsUtil.getAnnotationValue( annotation, "name" );
    if ( !SourceVersion.isIdentifier( name ) )
    {
      throw new ProcessorException( "@Router target has a route with an invalid name '" + name + "'",
                                    descriptor.getElement() );

    }
    final boolean navigationTarget = AnnotationsUtil.getAnnotationValue( annotation, "navigationTarget" );
    final boolean partialMatch = AnnotationsUtil.getAnnotationValue( annotation, "partialMatch" );
    final RouteDescriptor route = new RouteDescriptor( name, navigationTarget, partialMatch );

    if ( descriptor.hasRouteNamed( name ) )
    {
      throw new ProcessorException( "@Router target has multiple routes with the name '" + name + "'",
                                    descriptor.getElement() );
    }

    parseRoutePath( descriptor.getElement(), route, AnnotationsUtil.getAnnotationValue( annotation, "path" ) );

    descriptor.addRoute( route );
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

      throw new ProcessorException( "@Route named '" + route.getName() + "' has a path that can not " +
                                    "be parsed: '" + path + "'",
                                    typeElement );
    }
  }

  @Nullable
  private String deriveCallbackName( @Nonnull final ExecutableElement method, @Nonnull final String name )
    throws ProcessorException
  {
    if ( name.isEmpty() )
    {
      final String methodName = method.getSimpleName().toString();
      final Matcher matcher = RouterProcessor.CALLBACK_PATTERN.matcher( methodName );
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
