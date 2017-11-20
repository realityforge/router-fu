package router.fu.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

final class ProcessorUtil
{
  private ProcessorUtil()
  {
  }

  static void mustNotThrowAnyExceptions( @Nonnull final Class<? extends Annotation> type,
                                         @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    if ( !method.getThrownTypes().isEmpty() )
    {
      throw new RouterProcessorException( "@" + type.getSimpleName() + " target must not throw any exceptions",
                                          method );
    }
  }

  static void mustNotHaveAnyParameters( @Nonnull final Class<? extends Annotation> type,
                                        @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    if ( !method.getParameters().isEmpty() )
    {
      throw new RouterProcessorException( "@" + type.getSimpleName() + " target must not have any parameters", method );
    }
  }

  /**
   * Verifies that the method is not final, static, abstract or private.
   * The intent is to verify that it can be overridden in sub-class in same package.
   */
  static void mustBeOverridable( @Nonnull final Class<? extends Annotation> type,
                                 @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    mustNotBeFinal( type, method );
    mustBeSubclassCallable( type, method );
  }

  /**
   * Verifies that the method is not static, abstract or private.
   * The intent is to verify that it can be instance called by sub-class in same package.
   */
  private static void mustBeSubclassCallable( @Nonnull final Class<? extends Annotation> type,
                                              @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    mustNotBeStatic( type, method );
    mustNotBePrivate( type, method );
  }

  private static void mustNotBeFinal( @Nonnull final Class<? extends Annotation> type,
                                      @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    if ( method.getModifiers().contains( Modifier.FINAL ) )
    {
      throw new RouterProcessorException( "@" + type.getSimpleName() + " target must not be final", method );
    }
  }

  private static void mustNotBeStatic( @Nonnull final Class<? extends Annotation> type,
                                       @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    if ( method.getModifiers().contains( Modifier.STATIC ) )
    {
      throw new RouterProcessorException( "@" + type.getSimpleName() + " target must not be static", method );
    }
  }

  private static void mustNotBePrivate( @Nonnull final Class<? extends Annotation> type,
                                        @Nonnull final ExecutableElement method )
    throws RouterProcessorException
  {
    if ( method.getModifiers().contains( Modifier.PRIVATE ) )
    {
      throw new RouterProcessorException( "@" + type.getSimpleName() + " target must not be private", method );
    }
  }

  @Nonnull
  static List<ExecutableElement> getMethods( @Nonnull final TypeElement element,
                                             @Nonnull final Types typeUtils )
  {
    final Map<String, ExecutableElement> methodMap = new LinkedHashMap<>();
    enumerateMethods( element, typeUtils, element, methodMap );
    return new ArrayList<>( methodMap.values() );
  }

  private static void enumerateMethods( @Nonnull final TypeElement scope,
                                        @Nonnull final Types typeUtils,
                                        @Nonnull final TypeElement element,
                                        @Nonnull final Map<String, ExecutableElement> methods )
  {
    final TypeMirror superclass = element.getSuperclass();
    if ( TypeKind.NONE != superclass.getKind() )
    {
      enumerateMethods( scope, typeUtils, (TypeElement) ( (DeclaredType) superclass ).asElement(), methods );
    }
    for ( final TypeMirror interfaceType : element.getInterfaces() )
    {
      final TypeElement interfaceElement = (TypeElement) ( (DeclaredType) interfaceType ).asElement();
      enumerateMethods( scope, typeUtils, interfaceElement, methods );
    }
    for ( final Element member : element.getEnclosedElements() )
    {
      if ( member.getKind() == ElementKind.METHOD )
      {
        final ExecutableType methodType =
          (ExecutableType) typeUtils.asMemberOf( (DeclaredType) scope.asType(), member );
        methods.put( member.getSimpleName() + methodType.toString(), (ExecutableElement) member );
      }
    }
  }

  static void copyAccessModifiers( @Nonnull final TypeElement element, @Nonnull final TypeSpec.Builder builder )
  {
    if ( element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
    }
  }

  static void copyAccessModifiers( @Nonnull final ExecutableElement element, @Nonnull final MethodSpec.Builder builder )
  {
    if ( element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
    }
    else if ( element.getModifiers().contains( Modifier.PROTECTED ) )
    {
      builder.addModifiers( Modifier.PROTECTED );
    }
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

  static void copyTypeParameters( @Nonnull final TypeElement action, @Nonnull final TypeSpec.Builder builder )
  {
    for ( final TypeParameterElement typeParameter : action.getTypeParameters() )
    {
      builder.addTypeVariable( TypeVariableName.get( typeParameter ) );
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
}
