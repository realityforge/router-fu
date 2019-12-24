package router.fu.processor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import org.realityforge.proton.AnnotationsUtil;

final class ProcessorUtil
{
  private ProcessorUtil()
  {
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
