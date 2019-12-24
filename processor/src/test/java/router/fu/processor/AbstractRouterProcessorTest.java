package router.fu.processor;

import arez.processor.ArezProcessor;
import com.google.common.collect.ImmutableList;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.tools.JavaFileObject;
import static com.google.common.truth.Truth.*;
import static org.testng.Assert.*;

@SuppressWarnings( "Duplicates" )
abstract class AbstractRouterProcessorTest
{
  void assertSuccessfulCompile( @Nonnull final String classname )
    throws Exception
  {
    // It should be noted that we do not test the output of any Arez artifact
    // emitted. We assume the Arez project adequately tests this scenario
    final String[] elements = classname.contains( "." ) ? classname.split( "\\." ) : new String[]{ classname };
    final StringBuilder input = new StringBuilder();
    final StringBuilder service = new StringBuilder();
    final StringBuilder impl = new StringBuilder();
    input.append( "input" );
    service.append( "expected" );
    impl.append( "expected" );
    for ( int i = 0; i < elements.length; i++ )
    {
      input.append( '/' );
      input.append( elements[ i ] );
      service.append( '/' );
      service.append( elements[ i ] );
      if ( i == elements.length - 1 )
      {
        service.append( "Service" );
      }
      impl.append( '/' );
      if ( i == elements.length - 1 )
      {
        impl.append( "RouterFu_" );
      }
      impl.append( elements[ i ] );
    }
    input.append( ".java" );
    service.append( ".java" );
    impl.append( ".java" );
    assertSuccessfulCompile( input.toString(), service.toString(), impl.toString() );
  }

  void assertSuccessfulCompile( @Nonnull final String inputResource, @Nonnull final String... expectedOutputResources )
    throws Exception
  {
    final JavaFileObject source = fixture( inputResource );
    assertSuccessfulCompile( Collections.singletonList( source ), Arrays.asList( expectedOutputResources ) );
  }

  void assertSuccessfulCompile( @Nonnull final List<JavaFileObject> inputs, @Nonnull final List<String> outputs )
    throws Exception
  {
    // Arez processor required so that our tests emit all the right outputs
    // when outputFiles() is true
    if ( outputFiles() )
    {
      final Compilation compilation =
        Compiler
          .javac()
          .withOptions( "-Xlint:all,-processing", "-implicit:none", "-Arouter.fu.defer.errors=false" )
          .withProcessors( new RouterProcessor(), new ArezProcessor() )
          .compile( inputs );

      final Compilation.Status status = compilation.status();
      if ( Compilation.Status.SUCCESS != status )
      {
        /*
         * Ugly hackery that marks the compile as successful so we can emit output onto filesystem. This could
         * result in java code that is not compilable emitted to filesystem. This re-running determining problems
         * a little easier even if it does make re-running tests from IDE a little harder
         */
        final Field field = compilation.getClass().getDeclaredField( "status" );
        field.setAccessible( true );
        field.set( compilation, Compilation.Status.SUCCESS );
      }

      final ImmutableList<JavaFileObject> fileObjects = compilation.generatedSourceFiles();
      for ( final JavaFileObject fileObject : fileObjects )
      {
        final Path target = fixtureDir().resolve( "expected/" + fileObject.getName().replace( "/SOURCE_OUTPUT/", "" ) );
        final String filename = target.toFile().getName();
        if ( filename.startsWith( "Arez_" ) || filename.contains( "_Arez_" ) )
        {
          continue;
        }
        if ( Files.exists( target ) )
        {
          Files.delete( target );
        }

        final File dir = target.getParent().toFile();
        if ( !dir.exists() )
        {
          assertTrue( dir.mkdirs() );
        }
        Files.copy( fileObject.openInputStream(), target );
      }

      if ( Compilation.Status.SUCCESS != status )
      {
        // Restore old status
        final Field field = compilation.getClass().getDeclaredField( "status" );
        field.setAccessible( true );
        field.set( compilation, status );

        // This next line will generate an error
        //noinspection ResultOfMethodCallIgnored
        compilation.generatedSourceFiles();
      }
    }
    final JavaFileObject firstExpected = fixture( outputs.get( 0 ) );
    final JavaFileObject[] restExpected =
      outputs.stream().skip( 1 ).map( this::fixture ).toArray( JavaFileObject[]::new );
    assert_().about( JavaSourcesSubjectFactory.javaSources() ).
      that( inputs ).
      withCompilerOptions( "-Xlint:all,-processing", "-implicit:none", "-Arouter.fu.defer.errors=false" ).
      processedWith( new RouterProcessor(), new ArezProcessor() ).
      compilesWithoutError().
      and().
      generatesSources( firstExpected, restExpected );
  }

  void assertFailedCompile( @Nonnull final String classname, @Nonnull final String errorMessageFragment )
  {
    final String[] elements = classname.contains( "." ) ? classname.split( "\\." ) : new String[]{ classname };
    final StringBuilder input = new StringBuilder();
    input.append( "bad_input" );
    for ( final String element : elements )
    {
      input.append( '/' );
      input.append( element );
    }
    input.append( ".java" );
    assertFailedCompileResource( input.toString(), errorMessageFragment );
  }

  private void assertFailedCompileResource( @Nonnull final String inputResource,
                                            @Nonnull final String errorMessageFragment )
  {
    final JavaFileObject source = fixture( inputResource );
    assert_().about( JavaSourceSubjectFactory.javaSource() ).
      that( source ).
      withCompilerOptions( "-Xlint:all,-processing", "-implicit:none", "-Arouter.fu.defer.errors=false" ).
      processedWith( new RouterProcessor(), new ArezProcessor() ).
      failsToCompile().
      withErrorContaining( errorMessageFragment );
  }

  @Nonnull
  final JavaFileObject fixture( @Nonnull final String path )
  {
    try
    {
      return JavaFileObjects.forResource( fixtureDir().resolve( path ).toUri().toURL() );
    }
    catch ( final MalformedURLException e )
    {
      throw new IllegalStateException( e );
    }
  }

  @Nonnull
  private Path fixtureDir()
  {
    final String fixtureDir = System.getProperty( "router-fu.fixture_dir" );
    assertNotNull( fixtureDir, "Expected System.getProperty( \"router-fu.fixture_dir\" ) to return fixture directory" );
    return new File( fixtureDir ).toPath();
  }

  private boolean outputFiles()
  {
    return System.getProperty( "router-fu.output_fixture_data", "false" ).equals( "true" );
  }
}
