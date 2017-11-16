package router.fu.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import router.fu.Parameter;

final class Generator
{
  private static final ClassName REGEXP_TYPE = ClassName.get( "elemental2.core", "RegExp" );
  private static final String FIELD_PREFIX = "$fu$_";

  private Generator()
  {
  }

  @Nonnull
  static TypeSpec buildService( @Nonnull final RouterDescriptor descriptor )
  {
    final TypeElement element = descriptor.getElement();

    final TypeSpec.Builder builder = TypeSpec.interfaceBuilder( descriptor.getServiceClassName() );

    ProcessorUtil.copyAccessModifiers( element, builder );

    // Mark it as generated
    builder.addAnnotation( AnnotationSpec.builder( Generated.class ).
      addMember( "value", "$S", RouterProcessor.class.getName() ).
      build() );

    return builder.build();
  }

  @Nonnull
  static TypeSpec buildRouterImpl( @Nonnull final RouterDescriptor descriptor )
  {
    final TypeElement element = descriptor.getElement();

    final TypeSpec.Builder builder = TypeSpec.classBuilder( descriptor.getRouterClassName() );

    builder.superclass( descriptor.getClassName() );

    if ( descriptor.isArezComponent() )
    {
      final AnnotationSpec.Builder annotation =
        AnnotationSpec.builder( ClassName.get( "org.realityforge.arez.annotations", "ArezComponent" ) ).
          addMember( "type", "$S", descriptor.getClassName().simpleName() ).
          addMember( "nameIncludesId", "false" ).
          addMember( "allowEmpty", "true" );
      builder.addAnnotation( annotation.build() );
    }

    ProcessorUtil.copyAccessModifiers( element, builder );

    builder.addSuperinterface( descriptor.getServiceClassName() );

    // Mark it as generated
    builder.addAnnotation( AnnotationSpec.builder( Generated.class ).
      addMember( "value", "$S", RouterProcessor.class.getName() ).
      build() );

    buildParameterFields( builder, descriptor );

    return builder.build();
  }

  private static void buildParameterFields( @Nonnull final TypeSpec.Builder builder,
                                            @Nonnull final RouterDescriptor descriptor )
  {
    final Map<String, ParameterDescriptor> parameters =
      descriptor.getRoutes().stream().
        flatMap( r -> r.getParts()
          .stream()
          .filter( ParameterDescriptor.class::isInstance )
          .map( ParameterDescriptor.class::cast ) ).
        collect( Collectors.toMap( ParameterDescriptor::getKey, Function.identity(), ( s, a ) -> s ) );

    for ( final ParameterDescriptor parameter : parameters.values() )
    {
      final FieldSpec.Builder field =
        FieldSpec.builder( Parameter.class,
                           FIELD_PREFIX + parameter.getFieldName(),
                           Modifier.FINAL,
                           Modifier.PRIVATE );
      if ( null != parameter.getConstraint() )
      {
        field.initializer( "new $T( $S, new $T( $S ) )",
                           Parameter.class,
                           parameter.getName(),
                           REGEXP_TYPE,
                           parameter.getConstraint() );
      }
      else
      {
        field.initializer( "new $T( $S )", Parameter.class, parameter.getName() );
      }
      builder.addField( field.build() );
    }
  }
}
