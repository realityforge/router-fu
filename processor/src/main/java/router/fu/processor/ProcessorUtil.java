package router.fu.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import java.lang.annotation.Documented;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.Elements;
import org.realityforge.proton.AnnotationsUtil;
import org.realityforge.proton.MemberChecks;

final class ProcessorUtil
{
  private ProcessorUtil()
  {
  }


  static void copyDocumentedAnnotations( @Nonnull final AnnotatedConstruct element,
                                         @Nonnull final MethodSpec.Builder builder )
  {
    for ( final AnnotationMirror annotation : element.getAnnotationMirrors() )
    {
      final DeclaredType annotationType = annotation.getAnnotationType();
      if ( !annotationType.toString().startsWith( "router.fu.annotations." ) &&
           null != annotationType.asElement().getAnnotation( Documented.class ) )
      {
        builder.addAnnotation( AnnotationSpec.get( annotation ) );
      }
    }
  }

  static void copyDocumentedAnnotations( @Nonnull final AnnotatedConstruct element,
                                         @Nonnull final ParameterSpec.Builder builder )
  {
    for ( final AnnotationMirror annotation : element.getAnnotationMirrors() )
    {
      if ( null != annotation.getAnnotationType().asElement().getAnnotation( Documented.class ) )
      {
        builder.addAnnotation( AnnotationSpec.get( annotation ) );
      }
    }
  }

  static boolean isJavaIdentifier( @Nonnull final String value )
  {
    if ( value.isEmpty() || !Character.isJavaIdentifierStart( value.charAt( 0 ) ) )
    {
      return false;
    }
    else
    {
      final int length = value.length();
      for ( int i = 1; i < length; i++ )
      {
        if ( !Character.isJavaIdentifierPart( value.charAt( i ) ) )
        {
          return false;
        }
      }

      return true;
    }
  }

  @SuppressWarnings( "unchecked" )
  @Nonnull
  static List<AnnotationMirror> getRepeatingAnnotations( @Nonnull final Element typeElement,
                                                         @Nonnull final String containerClassName,
                                                         @Nonnull final String annotationClassName )
  {
    final AnnotationValue annotationValue =
      AnnotationsUtil.findAnnotationValue( typeElement, containerClassName, "value" );
    if ( null != annotationValue )
    {
      return ( (List<AnnotationValue>) annotationValue.getValue() ).stream().
        map( v -> (AnnotationMirror) v.getValue() ).collect( Collectors.toList() );
    }
    else
    {
      final AnnotationMirror annotation = AnnotationsUtil.findAnnotationByType( typeElement, annotationClassName );
      if ( null != annotation )
      {
        return Collections.singletonList( annotation );
      }
      else
      {
        return Collections.emptyList();
      }
    }
  }
}
