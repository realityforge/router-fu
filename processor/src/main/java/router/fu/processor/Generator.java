package router.fu.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.lang.model.element.TypeElement;

final class Generator
{
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

    return builder.build();
  }
}
