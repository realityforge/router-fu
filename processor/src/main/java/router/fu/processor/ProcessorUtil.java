package router.fu.processor;

import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

final class ProcessorUtil
{
  private ProcessorUtil()
  {
  }

  static void copyAccessModifiers( @Nonnull final TypeElement element, @Nonnull final TypeSpec.Builder builder )
  {
    if ( element.getModifiers().contains( Modifier.PUBLIC ) )
    {
      builder.addModifiers( Modifier.PUBLIC );
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
