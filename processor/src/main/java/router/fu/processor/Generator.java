package router.fu.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ExecutableType;
import org.realityforge.proton.GeneratorUtil;

final class Generator
{
  private static final ClassName NONNULL_CLASSNAME = ClassName.get( "javax.annotation", "Nonnull" );
  private static final ClassName NULLABLE_CLASSNAME = ClassName.get( "javax.annotation", "Nullable" );
  private static final ClassName REGEXP_TYPE = ClassName.get( "elemental2.core", "JsRegExp" );
  private static final ClassName ROUTER_TYPE = ClassName.get( "router.fu", "Router" );
  private static final ClassName ROUTE_TYPE = ClassName.get( "router.fu", "Route" );
  private static final ClassName ROUTE_STATE_TYPE = ClassName.get( "router.fu", "RouteState" );
  private static final ClassName SEGMENT_TYPE = ClassName.get( "router.fu", "Segment" );
  private static final ClassName PARAMETER_TYPE = ClassName.get( "router.fu", "Parameter" );
  private static final ClassName MATCH_RESULT_TYPE = ClassName.get( "router.fu", "MatchResult" );
  private static final ClassName BACKEND_TYPE = ClassName.get( "router.fu", "Backend" );
  private static final ClassName LOCATION_TYPE = ClassName.get( "router.fu", "Location" );
  private static final ClassName POST_CONSTRUCT_TYPE = ClassName.get( "arez.annotations", "PostConstruct" );
  private static final ClassName PRE_DISPOSE_TYPE = ClassName.get( "arez.annotations", "PreDispose" );
  private static final ClassName ACTION_TYPE = ClassName.get( "arez.annotations", "Action" );
  private static final ClassName OBSERVABLE_TYPE = ClassName.get( "arez.annotations", "Observable" );
  private static final ClassName OBSERVE_TYPE = ClassName.get( "arez.annotations", "Observe" );
  private static final ClassName EXECUTOR_TYPE = ClassName.get( "arez.annotations", "Executor" );
  private static final ClassName ON_DEPS_CHANGE_TYPE = ClassName.get( "arez.annotations", "OnDepsChange" );
  private static final ClassName FEATURE_TYPE = ClassName.get( "arez.annotations", "Feature" );
  private static final String FIELD_PREFIX = "$fu$_";
  private static final String ROUTE_FIELD_PREFIX = FIELD_PREFIX + "route_";
  private static final String ROUTE_STATE_FIELD_PREFIX = FIELD_PREFIX + "state_";
  private static final String BOUND_PARAMETER_FIELD_PREFIX = FIELD_PREFIX + "param_";

  private Generator()
  {
  }

  @Nonnull
  static TypeSpec buildService( @Nonnull final RouterDescriptor descriptor )
  {
    final TypeElement element = descriptor.getElement();

    final TypeSpec.Builder builder = TypeSpec.interfaceBuilder( descriptor.getServiceClassName() );

    GeneratorUtil.copyAccessModifiers( element, builder );

    // Mark it as generated
    builder.addAnnotation( AnnotationSpec.builder( Generated.class ).
      addMember( "value", "$S", RouterProcessor.class.getName() ).
      build() );

    buildGetLocationMethod( builder );
    descriptor.getRoutes().forEach( route -> {
      buildRouteMethod( builder, route );
      buildGetRouteStateMethod( builder, route );
      route.getParameters().forEach( parameter -> buildParameterAccessorMethod( builder, route, parameter ) );
    } );
    descriptor.getBoundParameters()
      .forEach( boundParameter -> {
        buildBoundParameterAccessor( builder, boundParameter );
        buildBoundParameterUpdater( builder, boundParameter );
      } );
    descriptor.getRoutes().stream().
      filter( RouteDescriptor::isNavigationTarget ).
      forEach( route -> {
        buildBuildLocationMethod( builder, route );
        buildGotoLocationMethod( builder, route );
      } );

    builder.addMethod( MethodSpec.methodBuilder( "reRoute" )
                         .addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT )
                         .build() );

    return builder.build();
  }

  private static void buildRouterRefMethod( @Nonnull final TypeSpec.Builder builder,
                                            @Nonnull final RouterDescriptor descriptor,
                                            @Nonnull final ExecutableElement routerRefMethod )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( routerRefMethod.getSimpleName().toString() );
    GeneratorUtil.copyAccessModifiers( routerRefMethod, method );
    GeneratorUtil.copyWhitelistedAnnotations( routerRefMethod, method );
    method.returns( descriptor.getServiceClassName() );
    method.addStatement( "return this" );
    builder.addMethod( method.build() );
  }

  private static void buildGetLocationMethod( @Nonnull final TypeSpec.Builder builder )
  {
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "getLocation" );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.returns( LOCATION_TYPE );
    builder.addMethod( method.build() );
  }

  private static void buildRouteMethod( @Nonnull final TypeSpec.Builder builder, @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( route.getName() ) + "Route" );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.returns( ROUTE_TYPE );
    builder.addMethod( method.build() );
  }

  private static void buildGetRouteStateMethod( @Nonnull final TypeSpec.Builder builder,
                                                @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( route.getName() ) + "RouteState" );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NULLABLE_CLASSNAME );
    method.returns( ROUTE_STATE_TYPE );
    builder.addMethod( method.build() );
  }

  private static void buildParameterAccessorMethod( @Nonnull final TypeSpec.Builder builder,
                                                    @Nonnull final RouteDescriptor route,
                                                    @Nonnull final ParameterDescriptor parameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" +
                                toPascalCaseName( route.getName() ) +
                                toPascalCaseName( parameter.getName() ) +
                                "Parameter" );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.returns( PARAMETER_TYPE );
    builder.addMethod( method.build() );
  }

  private static void buildBuildLocationMethod( @Nonnull final TypeSpec.Builder builder,
                                                @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "build" + toPascalCaseName( route.getName() ) + "Location" );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.returns( String.class );
    for ( final ParameterDescriptor parameter : route.getParameters() )
    {
      method.addParameter( ParameterSpec.builder( String.class, parameter.getName() )
                             .addAnnotation( NONNULL_CLASSNAME )
                             .build() );
    }
    builder.addMethod( method.build() );
  }

  private static void buildGotoLocationMethod( @Nonnull final TypeSpec.Builder builder,
                                               @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "goto" + toPascalCaseName( route.getName() ) );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NONNULL_CLASSNAME );
    for ( final ParameterDescriptor parameter : route.getParameters() )
    {
      method.addParameter( ParameterSpec.builder( String.class, parameter.getName() )
                             .addAnnotation( NONNULL_CLASSNAME )
                             .build() );
    }
    builder.addMethod( method.build() );
  }

  @Nonnull
  static TypeSpec buildRouterImpl( @Nonnull final RouterDescriptor descriptor )
  {
    final TypeElement element = descriptor.getElement();

    final TypeSpec.Builder builder = TypeSpec.classBuilder( descriptor.getRouterClassName() );

    GeneratorUtil.copyTypeParameters( descriptor.getElement(), builder );

    builder.superclass( TypeName.get( descriptor.getElement().asType() ) );

    if ( descriptor.isArezComponent() )
    {
      final AnnotationSpec.Builder annotation =
        AnnotationSpec.builder( ClassName.get( "arez.annotations", "ArezComponent" ) ).
          addMember( "name", "$S", descriptor.getClassName().simpleName() ).
          addMember( "requireId", "$T.DISABLE", FEATURE_TYPE ).
          addMember( "disposeNotifier", "$T.DISABLE", FEATURE_TYPE );
      builder.addAnnotation( annotation.build() );
      builder.addModifiers( Modifier.ABSTRACT );
    }

    GeneratorUtil.copyAccessModifiers( element, builder );

    builder.addSuperinterface( descriptor.getServiceClassName() );

    // Mark it as generated
    builder.addAnnotation( AnnotationSpec.builder( Generated.class ).
      addMember( "value", "$S", RouterProcessor.class.getName() ).
      build() );

    buildParameterFields( builder, descriptor );
    descriptor.getRoutes().forEach( route -> buildRouteField( builder, route ) );
    //Add router field
    builder.addField( ROUTER_TYPE, FIELD_PREFIX + "router", Modifier.FINAL, Modifier.PRIVATE );
    builder.addField( LOCATION_TYPE, FIELD_PREFIX + "location", Modifier.PRIVATE );
    descriptor.getRoutes().forEach( route -> buildRouteStateField( builder, route ) );
    descriptor.getBoundParameters().forEach( boundParameter -> buildBoundParameterField( builder, boundParameter ) );

    buildConstructor( builder, descriptor );
    if ( descriptor.isArezComponent() )
    {
      buildPostConstruct( builder );
      buildPreDispose( builder );
    }

    descriptor.getRoutes().forEach( route -> {
      buildRouteMethodImpl( builder, route );
      buildGetRouteStateMethodImpl( builder, descriptor, route );
      buildSetRouteStateMethodImpl( builder, route );
      route.getParameters().forEach( parameter -> buildParameterAccessorImplMethod( builder, route, parameter ) );
      if ( route.hasCallback() && descriptor.isArezComponent() )
      {
        // Override this to make it @Track method that causes a re-route on change
        buildCallbackWrapperMethodImpl( builder, route );
        buildCallbackDepsChangedMethodImpl( builder, route );
      }
    } );

    descriptor.getBoundParameters().forEach( boundParameter -> {
      buildBoundParameterAccessorImpl( builder, descriptor, boundParameter );
      buildBoundParameterMutator( builder, boundParameter );
      buildBoundParameterUpdaterImpl( builder, descriptor, boundParameter );
    } );

    descriptor.getRoutes().stream().
      filter( RouteDescriptor::isNavigationTarget ).
      forEach( route -> {
        buildBuildLocationMethodImpl( builder, route );
        buildGotoLocationMethodImpl( builder, route );
      } );

    buildGetLocationImplMethod( builder, descriptor );
    buildSetLocationMethod( builder );
    buildOnLocationChangedMethod( builder, descriptor );

    descriptor.getRouterRefMethods()
      .forEach( routerRefMethod -> buildRouterRefMethod( builder, descriptor, routerRefMethod ) );

    buildReRouteMethodImpl( builder );

    return builder.build();
  }

  private static void buildReRouteMethodImpl( @Nonnull final TypeSpec.Builder builder )
  {
    builder.addMethod( MethodSpec.methodBuilder( "reRoute" )
                         .addAnnotation( Override.class )
                         .addModifiers( Modifier.PUBLIC )
                         .addStatement( "$N.reRoute()", FIELD_PREFIX + "router" )
                         .build() );
  }

  private static void buildConstructor( @Nonnull final TypeSpec.Builder builder,
                                        @Nonnull final RouterDescriptor descriptor )
  {
    final MethodSpec.Builder ctor = MethodSpec.constructorBuilder();
    ctor.addParameter( ParameterSpec.builder( BACKEND_TYPE, "backend", Modifier.FINAL ).
      addAnnotation( NONNULL_CLASSNAME ).build() );

    final StringBuilder sb = new StringBuilder();
    final ArrayList<Object> params = new ArrayList<>();
    sb.append( "$N = new $T( this::onLocationChanged, backend, $T.unmodifiableList( $T.asList( " );
    params.add( FIELD_PREFIX + "router" );
    params.add( ROUTER_TYPE );
    params.add( Collections.class );
    params.add( Arrays.class );
    sb.append( descriptor.getRoutes()
                 .stream()
                 .map( route -> ROUTE_FIELD_PREFIX + route.getName() )
                 .peek( params::add )
                 .map( routeName -> "$N" )
                 .collect( Collectors.joining( ", " ) ) );
    sb.append( " ) ) )" );
    ctor.addStatement( sb.toString(), params.toArray() );
    if ( descriptor.isArezComponent() )
    {
      ctor.addStatement( "$N = new $T( $S, $T.emptyList() )",
                         FIELD_PREFIX + "location",
                         LOCATION_TYPE,
                         "",
                         Collections.class );
    }
    else
    {
      ctor.addStatement( "$N.activate()", FIELD_PREFIX + "router" );
    }
    builder.addMethod( ctor.build() );
  }

  private static void buildPostConstruct( @Nonnull final TypeSpec.Builder builder )
  {
    builder.addMethod( MethodSpec
                         .methodBuilder( "postConstruct" )
                         .addAnnotation( POST_CONSTRUCT_TYPE )
                         .addStatement( "$N.activate()", FIELD_PREFIX + "router" )
                         .build() );
  }

  private static void buildPreDispose( @Nonnull final TypeSpec.Builder builder )
  {
    builder.addMethod( MethodSpec
                         .methodBuilder( "preDispose" )
                         .addAnnotation( PRE_DISPOSE_TYPE )
                         .addStatement( "$N.deactivate()", FIELD_PREFIX + "router" )
                         .build() );
  }

  private static void buildParameterFields( @Nonnull final TypeSpec.Builder builder,
                                            @Nonnull final RouterDescriptor descriptor )
  {
    final Map<String, ParameterDescriptor> parameters =
      descriptor.getRoutes().stream().
        flatMap( r -> r.getParameters().stream() ).
        collect( Collectors.toMap( ParameterDescriptor::getKey, Function.identity(), ( s, a ) -> s ) );

    parameters.values().forEach( parameter -> buildParameterField( builder, parameter ) );
  }

  private static void buildParameterField( @Nonnull final TypeSpec.Builder builder,
                                           @Nonnull final ParameterDescriptor parameter )
  {
    final FieldSpec.Builder field =
      FieldSpec.builder( PARAMETER_TYPE,
                         FIELD_PREFIX + parameter.getFieldName(),
                         Modifier.FINAL,
                         Modifier.PRIVATE );
    final String constraint = parameter.getConstraint();
    if ( null != constraint )
    {
      field.initializer( "new $T( $S, new $T( $S ) )",
                         PARAMETER_TYPE,
                         parameter.getName(),
                         REGEXP_TYPE,
                         "^" + constraint + "$" );
    }
    else
    {
      field.initializer( "new $T( $S )", PARAMETER_TYPE, parameter.getName() );
    }
    builder.addField( field.build() );
  }

  private static void buildRouteStateField( @Nonnull final TypeSpec.Builder builder,
                                            @Nonnull final RouteDescriptor route )
  {
    final FieldSpec.Builder field =
      FieldSpec.builder( ROUTE_STATE_TYPE, ROUTE_STATE_FIELD_PREFIX + route.getName(), Modifier.PRIVATE );
    builder.addField( field.build() );
  }

  private static void buildBoundParameterField( @Nonnull final TypeSpec.Builder builder,
                                                @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final FieldSpec.Builder field =
      FieldSpec.builder( String.class, BOUND_PARAMETER_FIELD_PREFIX + boundParameter.getName(), Modifier.PRIVATE );
    builder.addField( field.build() );
  }

  private static void buildRouteField( @Nonnull final TypeSpec.Builder builder, @Nonnull final RouteDescriptor route )
  {
    final FieldSpec.Builder field =
      FieldSpec.builder( ROUTE_TYPE,
                         ROUTE_FIELD_PREFIX + route.getName(),
                         Modifier.FINAL,
                         Modifier.PRIVATE );
    final StringBuilder sb = new StringBuilder();
    final ArrayList<Object> params = new ArrayList<>();
    sb.append( "new $T( $S, " );
    params.add( ROUTE_TYPE );
    params.add( route.getName() );

    sb.append( "new $T[]{" );
    params.add( SEGMENT_TYPE );
    buildSegments( sb, params, route );
    sb.append( "}, " );

    sb.append( "new $T[]{" );
    params.add( PARAMETER_TYPE );
    buildParameters( sb, params, route );
    sb.append( "}, " );

    sb.append( "new $T( $S ), " );
    params.add( REGEXP_TYPE );
    params.add( toJsRegExp( route ) );

    if ( route.hasCallback() )
    {
      sb.append( "( location, route, parameters ) -> $N( " );
      params.add( route.getCallback().getSimpleName().toString() );
      for ( int i = 0; i < 3; i++ )
      {
        if ( i == route.getLocationIndex() )
        {
          if ( i != 0 )
          {
            sb.append( ", " );
          }
          sb.append( "location" );
        }
        else if ( i == route.getRouteIndex() )
        {
          if ( i != 0 )
          {
            sb.append( ", " );
          }
          sb.append( "route" );
        }
        else if ( i == route.getParametersIndex() )
        {
          if ( i != 0 )
          {
            sb.append( ", " );
          }
          sb.append( "parameters" );
        }
        else
        {
          break;
        }
      }
      sb.append( " ) )" );
    }
    else
    {
      sb.append( "( location, route, parameters ) -> $T.$N )" );
      params.add( MATCH_RESULT_TYPE );
      params.add( route.isNavigationTarget() ? "TERMINAL" : "NON_TERMINAL" );
    }

    field.initializer( sb.toString(), params.toArray() );
    builder.addField( field.build() );
  }

  @Nonnull
  private static String toJsRegExp( @Nonnull final RouteDescriptor route )
  {
    final StringBuilder sb = new StringBuilder();
    sb.append( "^/" );
    for ( final Object part : route.getParts() )
    {
      if ( part instanceof String )
      {
        sb.append( part );
      }
      else
      {
        sb.append( "((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)" );
      }
    }
    if ( !route.isPartialMatch() )
    {
      sb.append( "$" );
    }
    return sb.toString();
  }

  private static void buildSegments( @Nonnull final StringBuilder sb,
                                     @Nonnull final ArrayList<Object> params,
                                     @Nonnull final RouteDescriptor route )
  {
    final StringBuilder accumulator = new StringBuilder();
    if ( !route.getParts().isEmpty() )
    {
      accumulator.append( "/" );
    }
    for ( final Object part : route.getParts() )
    {
      if ( part instanceof String )
      {
        accumulator.append( part );
      }
      else
      {
        if ( 0 != accumulator.length() )
        {
          sb.append( "new $T( $S ), " );
          params.add( SEGMENT_TYPE );
          params.add( accumulator.toString() );
          accumulator.setLength( 0 );
        }
        final ParameterDescriptor param = (ParameterDescriptor) part;
        sb.append( "new $T( $N ), " );
        params.add( SEGMENT_TYPE );
        params.add( FIELD_PREFIX + param.getFieldName() );
      }
    }
    if ( 0 != accumulator.length() )
    {
      sb.append( "new $T( $S ) " );
      params.add( SEGMENT_TYPE );
      params.add( accumulator.toString() );
    }
  }

  private static void buildParameters( @Nonnull final StringBuilder sb,
                                       @Nonnull final ArrayList<Object> params,
                                       @Nonnull final RouteDescriptor route )
  {
    for ( final Object part : route.getParts() )
    {
      if ( part instanceof ParameterDescriptor )
      {
        final ParameterDescriptor param = (ParameterDescriptor) part;
        sb.append( "$N, " );
        params.add( FIELD_PREFIX + param.getFieldName() );
      }
    }
  }

  private static void buildRouteMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                            @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( route.getName() ) + "Route" );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.addAnnotation( Override.class );
    method.returns( ROUTE_TYPE );
    method.addStatement( "return $N", ROUTE_FIELD_PREFIX + route.getName() );
    builder.addMethod( method.build() );
  }

  private static void buildBoundParameterUpdater( @Nonnull final TypeSpec.Builder builder,
                                                  @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "update" + toPascalCaseName( boundParameter.getName() ) );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addParameter( ParameterSpec.builder( String.class, boundParameter.getName() )
                           .addAnnotation( NONNULL_CLASSNAME )
                           .build() );
    builder.addMethod( method.build() );
  }

  private static void buildBoundParameterAccessor( @Nonnull final TypeSpec.Builder builder,
                                                   @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( boundParameter.getName() ) );
    method.addModifiers( Modifier.PUBLIC, Modifier.ABSTRACT );
    method.addAnnotation( NULLABLE_CLASSNAME );
    method.returns( String.class );
    builder.addMethod( method.build() );
  }

  private static void buildBoundParameterUpdaterImpl( @Nonnull final TypeSpec.Builder builder,
                                                      @Nonnull final RouterDescriptor descriptor,
                                                      @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "update" + toPascalCaseName( boundParameter.getName() ) );
    method.addModifiers( Modifier.PUBLIC );
    if ( descriptor.isArezComponent() )
    {
      method.addAnnotation( ACTION_TYPE );
    }
    method.addAnnotation( Override.class );
    method.addParameter( ParameterSpec.builder( String.class, boundParameter.getName(), Modifier.FINAL )
                           .addAnnotation( NONNULL_CLASSNAME )
                           .build() );
    method.addStatement( "final $T location = getLocation()", LOCATION_TYPE );
    method.addStatement( "final $T terminalState = location.getTerminalState()", ROUTE_STATE_TYPE );
    final CodeBlock.Builder block = CodeBlock.builder();
    block.beginControlFlow( "if ( null != terminalState )" );
    block.addStatement( "final $T route = terminalState.getRoute()", ROUTE_TYPE );

    final CodeBlock.Builder routeBlocks = CodeBlock.builder();
    final LinkedHashMap<RouteDescriptor, ParameterDescriptor> bindings = boundParameter.getBindings();
    boolean needElse = false;
    for ( final Map.Entry<RouteDescriptor, ParameterDescriptor> entry : bindings.entrySet() )
    {
      final RouteDescriptor route = entry.getKey();
      if ( !route.isNavigationTarget() )
      {
        continue;
      }
      if ( needElse )
      {
        routeBlocks.nextControlFlow( "else if ( route == $N )",
                                     ROUTE_FIELD_PREFIX + route.getName() );

      }
      else
      {

        routeBlocks.beginControlFlow( "if ( route == $N )",
                                      ROUTE_FIELD_PREFIX + route.getName() );
        needElse = true;
      }

      final StringBuilder sb = new StringBuilder();
      final ArrayList<Object> params = new ArrayList<>();

      sb.append( "$N(" );
      params.add( "goto" + toPascalCaseName( route.getName() ) );

      final AtomicBoolean comma = new AtomicBoolean();
      route.getParameters().forEach( p -> {
        sb.append( comma.get() ? ", " : "" );
        comma.set( true );
        if ( p == boundParameter.getBindings().get( route ) )
        {
          sb.append( "$N" );
          params.add( boundParameter.getName() );
        }
        else
        {
          sb.append( "terminalState.getParameterValue( $N )" );
          params.add( FIELD_PREFIX + p.getFieldName() );
        }

      } );

      sb.append( ")" );
      routeBlocks.addStatement( sb.toString(), params.toArray() );
    }

    routeBlocks.endControlFlow();
    block.add( routeBlocks.build() );
    block.endControlFlow();
    method.addCode( block.build() );
    builder.addMethod( method.build() );
  }

  private static void buildBoundParameterAccessorImpl( @Nonnull final TypeSpec.Builder builder,
                                                       @Nonnull final RouterDescriptor descriptor,
                                                       @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( boundParameter.getName() ) );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NULLABLE_CLASSNAME );
    if ( descriptor.isArezComponent() )
    {
      method.addAnnotation( OBSERVABLE_TYPE );
    }
    method.addAnnotation( Override.class );
    method.returns( String.class );
    method.addStatement( "return $N", BOUND_PARAMETER_FIELD_PREFIX + boundParameter.getName() );
    builder.addMethod( method.build() );
  }

  private static void buildBoundParameterMutator( @Nonnull final TypeSpec.Builder builder,
                                                  @Nonnull final BoundParameterDescriptor boundParameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "set" + toPascalCaseName( boundParameter.getName() ) );
    final ParameterSpec.Builder parameter =
      ParameterSpec.builder( String.class, "value", Modifier.FINAL ).addAnnotation( NULLABLE_CLASSNAME );
    method.addParameter( parameter.build() );
    method.addStatement( "$N = value", BOUND_PARAMETER_FIELD_PREFIX + boundParameter.getName() );
    builder.addMethod( method.build() );
  }

  private static void buildGetRouteStateMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                    @Nonnull final RouterDescriptor descriptor,
                                                    @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" + toPascalCaseName( route.getName() ) + "RouteState" );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NULLABLE_CLASSNAME );
    if ( descriptor.isArezComponent() )
    {
      method.addAnnotation( OBSERVABLE_TYPE );
    }
    method.addAnnotation( Override.class );
    method.returns( ROUTE_STATE_TYPE );
    method.addStatement( "return $N", ROUTE_STATE_FIELD_PREFIX + route.getName() );
    builder.addMethod( method.build() );
  }

  private static void buildSetRouteStateMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                    @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "set" + toPascalCaseName( route.getName() ) + "RouteState" );
    final ParameterSpec.Builder parameter =
      ParameterSpec.builder( ROUTE_STATE_TYPE, "state", Modifier.FINAL ).addAnnotation( NULLABLE_CLASSNAME );
    method.addParameter( parameter.build() );
    method.addStatement( "$N = state", ROUTE_STATE_FIELD_PREFIX + route.getName() );
    builder.addMethod( method.build() );
  }

  private static void buildParameterAccessorImplMethod( @Nonnull final TypeSpec.Builder builder,
                                                        @Nonnull final RouteDescriptor route,
                                                        @Nonnull final ParameterDescriptor parameter )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "get" +
                                toPascalCaseName( route.getName() ) +
                                toPascalCaseName( parameter.getName() ) +
                                "Parameter" );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.addAnnotation( Override.class );
    method.returns( PARAMETER_TYPE );
    method.addStatement( "return $N", FIELD_PREFIX + parameter.getFieldName() );
    builder.addMethod( method.build() );
  }

  private static void buildCallbackWrapperMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                      @Nonnull final RouteDescriptor route )
  {
    final ExecutableElement callback = route.getCallback();
    final ExecutableType callbackType = route.getCallbackType();
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( callback.getSimpleName().toString() );
    method.addAnnotation( AnnotationSpec.builder( OBSERVE_TYPE )
                            .addMember( "executor", "$T.EXTERNAL", EXECUTOR_TYPE )
                            .build() );
    method.returns( MATCH_RESULT_TYPE );
    GeneratorUtil.copyWhitelistedAnnotations( callback, method );

    final StringBuilder statement = new StringBuilder();
    final ArrayList<Object> parameterNames = new ArrayList<>();
    statement.append( "return super.$N(" );
    parameterNames.add( callback.getSimpleName().toString() );

    boolean firstParam = true;
    final List<? extends VariableElement> parameters = callback.getParameters();
    final int paramCount = parameters.size();
    for ( int i = 0; i < paramCount; i++ )
    {
      final VariableElement element = parameters.get( i );
      final TypeName parameterType = TypeName.get( callbackType.getParameterTypes().get( i ) );
      final ParameterSpec.Builder param =
        ParameterSpec.builder( parameterType, element.getSimpleName().toString(), Modifier.FINAL );
      GeneratorUtil.copyWhitelistedAnnotations( element, param );
      method.addParameter( param.build() );

      parameterNames.add( element.getSimpleName().toString() );
      if ( !firstParam )
      {
        statement.append( "," );
      }
      firstParam = false;
      statement.append( "$N" );
    }
    statement.append( ")" );
    method.addStatement( statement.toString(), parameterNames.toArray() );
    builder.addMethod( method.build() );
  }

  private static void buildCallbackDepsChangedMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                          @Nonnull final RouteDescriptor route )
  {
    builder.addMethod( MethodSpec
                         .methodBuilder( "on" + toPascalCaseName( route.getName() + "Callback" ) + "DepsChange" )
                         .addAnnotation( ON_DEPS_CHANGE_TYPE )
                         .addStatement( "reRoute()" )
                         .build() );
  }

  private static void buildBuildLocationMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                    @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "build" + toPascalCaseName( route.getName() ) + "Location" );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.addAnnotation( Override.class );
    method.returns( String.class );

    method.addStatement( "final $T<$T, $T> $N = new $T<>()",
                         Map.class,
                         PARAMETER_TYPE,
                         String.class,
                         ROUTE_FIELD_PREFIX + "params",
                         HashMap.class );
    for ( final ParameterDescriptor parameter : route.getParameters() )
    {
      method.addParameter( ParameterSpec.builder( String.class, parameter.getName(), Modifier.FINAL )
                             .addAnnotation( NONNULL_CLASSNAME )
                             .build() );
      method.addStatement( "$N.put( $N, $N )",
                           ROUTE_FIELD_PREFIX + "params",
                           FIELD_PREFIX + parameter.getFieldName(),
                           parameter.getName() );
    }
    method.addStatement( "return $N.buildLocation( $N )",
                         ROUTE_FIELD_PREFIX + route.getName(),
                         ROUTE_FIELD_PREFIX + "params" );
    builder.addMethod( method.build() );
  }

  private static void buildGotoLocationMethodImpl( @Nonnull final TypeSpec.Builder builder,
                                                   @Nonnull final RouteDescriptor route )
  {
    final MethodSpec.Builder method =
      MethodSpec.methodBuilder( "goto" + toPascalCaseName( route.getName() ) );
    method.addModifiers( Modifier.PUBLIC );
    method.addAnnotation( NONNULL_CLASSNAME );
    method.addAnnotation( Override.class );
    for ( final ParameterDescriptor parameter : route.getParameters() )
    {
      method.addParameter( ParameterSpec.builder( String.class, parameter.getName(), Modifier.FINAL )
                             .addAnnotation( NONNULL_CLASSNAME )
                             .build() );
    }

    final StringBuilder sb = new StringBuilder();
    final ArrayList<Object> params = new ArrayList<>();
    sb.append( "$N.changeLocation( $N( " );
    params.add( FIELD_PREFIX + "router" );
    params.add( "build" + toPascalCaseName( route.getName() ) + "Location" );
    sb.append( route.getParameters()
                 .stream()
                 .map( ParameterDescriptor::getName )
                 .peek( params::add )
                 .map( routeName -> "$N" )
                 .collect( Collectors.joining( ", " ) ) );
    sb.append( " ) )" );
    method.addStatement( sb.toString(), params.toArray() );
    builder.addMethod( method.build() );
  }

  private static void buildGetLocationImplMethod( @Nonnull final TypeSpec.Builder builder,
                                                  @Nonnull final RouterDescriptor descriptor )
  {
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "getLocation" );
    method.addAnnotation( NONNULL_CLASSNAME );
    if ( descriptor.isArezComponent() )
    {
      method.addAnnotation( OBSERVABLE_TYPE );
    }
    method.addAnnotation( Override.class );
    method.addModifiers( Modifier.PUBLIC );
    method.returns( LOCATION_TYPE );
    method.addStatement( "assert null != $N", FIELD_PREFIX + "location" );
    method.addStatement( "return $N", FIELD_PREFIX + "location" );
    builder.addMethod( method.build() );
  }

  private static void buildSetLocationMethod( @Nonnull final TypeSpec.Builder builder )
  {
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "setLocation" );
    method.addParameter( ParameterSpec.builder( LOCATION_TYPE, "location", Modifier.FINAL )
                           .addAnnotation( NONNULL_CLASSNAME )
                           .build() );
    method.addStatement( "$N = location", FIELD_PREFIX + "location" );
    builder.addMethod( method.build() );
  }

  private static void buildOnLocationChangedMethod( @Nonnull final TypeSpec.Builder builder,
                                                    @Nonnull final RouterDescriptor descriptor )
  {
    final MethodSpec.Builder method = MethodSpec.methodBuilder( "onLocationChanged" );
    if ( descriptor.isArezComponent() )
    {
      method.addAnnotation( ACTION_TYPE );
    }
    method.addParameter( ParameterSpec.builder( LOCATION_TYPE, "location", Modifier.FINAL )
                           .addAnnotation( NONNULL_CLASSNAME )
                           .build() );
    method.addStatement( "setLocation( $T.requireNonNull( location ) )", Objects.class );
    for ( final BoundParameterDescriptor boundParameter : descriptor.getBoundParameters() )
    {
      method.addStatement( "$N( null )", "set" + toPascalCaseName( boundParameter.getName() ) );
    }
    method.addStatement( "final $T<$T> states = location.getStates()", List.class, ROUTE_STATE_TYPE );
    method.addStatement( "int routeStartIndex = 0", List.class, ROUTE_STATE_TYPE );

    final CodeBlock.Builder loop = CodeBlock.builder();
    final Collection<RouteDescriptor> routes = descriptor.getRoutes();
    loop.beginControlFlow( "for ( int i = 0; i < " + routes.size() + "; i++ )" );
    loop.addStatement( "final $T state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null",
                       ROUTE_STATE_TYPE );
    final CodeBlock.Builder switchBlock = CodeBlock.builder();
    switchBlock.beginControlFlow( "switch ( i )" );
    final CodeBlock.Builder bodyBlock = CodeBlock.builder();
    int index = 0;
    for ( final RouteDescriptor route : routes )
    {
      if ( 0 == index )
      {
        bodyBlock.beginControlFlow( "case " + index + ":" );
      }
      else
      {
        bodyBlock.nextControlFlow( "case " + index + ":" );
      }
      final CodeBlock.Builder caseBlock = CodeBlock.builder();
      caseBlock.beginControlFlow( "if ( null != state && state.getRoute() == $N )",
                                  ROUTE_FIELD_PREFIX + route.getName() );
      caseBlock.addStatement( "$N( state )", "set" + toPascalCaseName( route.getName() ) + "RouteState" );
      caseBlock.addStatement( "routeStartIndex++" );
      final Map<ParameterDescriptor, BoundParameterDescriptor> boundParameters = route.getBoundParameters();
      if ( !boundParameters.isEmpty() )
      {
        for ( final Map.Entry<ParameterDescriptor, BoundParameterDescriptor> entry : boundParameters.entrySet() )
        {
          caseBlock.addStatement( "$N( state.getParameterValue( $N ) )",
                                  "set" + toPascalCaseName( entry.getValue().getName() ),
                                  FIELD_PREFIX + entry.getKey().getFieldName() );
        }
      }

      caseBlock.nextControlFlow( "else" );
      caseBlock.addStatement( "$N( null )", "set" + toPascalCaseName( route.getName() ) + "RouteState" );
      caseBlock.endControlFlow();

      bodyBlock.add( caseBlock.build() );
      bodyBlock.addStatement( "break" );
      index++;
    }
    if ( 0 == index )
    {
      bodyBlock.beginControlFlow( "default:" );
    }
    else
    {
      bodyBlock.nextControlFlow( "default:" );
    }

    bodyBlock.addStatement( "break" );
    bodyBlock.endControlFlow();
    switchBlock.add( bodyBlock.build() );
    switchBlock.endControlFlow();
    loop.add( switchBlock.build() );
    loop.endControlFlow();
    method.addCode( loop.build() );

    builder.addMethod( method.build() );
  }

  @Nonnull
  private static String toPascalCaseName( @Nonnull final String name )
  {
    if ( Character.isUpperCase( name.charAt( 0 ) ) )
    {
      return name;
    }
    else
    {
      return Character.toUpperCase( name.charAt( 0 ) ) + ( name.length() > 1 ? name.substring( 1 ) : "" );
    }
  }
}
