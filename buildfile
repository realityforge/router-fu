require 'buildr/git_auto_version'
require 'buildr/gpg'
require 'buildr/single_intermediate_layout'
require 'buildr/gwt'
require 'buildr/jacoco'

PROVIDED_DEPS = [:javax_jsr305, :anodoc]
TEST_DEPS = [:guiceyloops]

# JDK options passed to test environment. Essentially turns assertions on.
TEST_OPTIONS =
  {
    'braincheck.environment' => 'development'
  }

desc 'router-fu: A GWT based state router'
define 'router-fu' do
  project.group = 'org.realityforge.router.fu'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  desc 'Annotations for defining a router'
  define 'annotations' do
    pom.provided_dependencies.concat PROVIDED_DEPS

    compile.with PROVIDED_DEPS

    gwt_enhance(project)

    package(:jar)
    package(:sources)
    package(:javadoc)

    # This dependency is added to make it easy to cross reference
    # core classes in javadocs but code should not make use of it.
    iml.main_dependencies << project('core').package(:jar)
  end

  desc 'The core router-fu code'
  define 'core' do
    pom.provided_dependencies.concat PROVIDED_DEPS

    compile.with PROVIDED_DEPS,
                 :jsinterop_base,
                 :jsinterop_base_sources,
                 :jsinterop_annotations,
                 :jsinterop_annotations_sources,
                 :elemental2_core,
                 :elemental2_dom,
                 :elemental2_promise,
                 :braincheck

    test.options[:properties] = TEST_OPTIONS
    test.options[:java_args] = ['-ea']

    gwt_enhance(project)

    package(:jar)
    package(:sources)
    package(:javadoc)

    test.using :testng
    test.compile.with TEST_DEPS
  end

  desc 'The Annotation processor'
  define 'processor' do
    pom.provided_dependencies.concat PROVIDED_DEPS

    compile.with :autoservice,
                 :autocommon,
                 :javapoet,
                 :guava,
                 :javax_jsr305

    test.with :compile_testing,
              Java.tools_jar,
              :truth,
              :arez_annotations,
              :arez_core,
              :arez_processor,
              :arez_component,
              project('annotations').package(:jar),
              project('annotations').compile.dependencies,
              project('core').package(:jar),
              project('core').compile.dependencies

    package(:jar)
    package(:sources)
    package(:javadoc)

    package(:jar).enhance do |jar|
      jar.merge(artifact(:javapoet))
      jar.merge(artifact(:guava))
      jar.enhance do |f|
        shaded_jar = (f.to_s + '-shaded')
        Buildr.ant 'shade_jar' do |ant|
          artifact = Buildr.artifact(:shade_task)
          artifact.invoke
          ant.taskdef :name => 'shade', :classname => 'org.realityforge.ant.shade.Shade', :classpath => artifact.to_s
          ant.shade :jar => f.to_s, :uberJar => shaded_jar do
            ant.relocation :pattern => 'com.squareup.javapoet', :shadedPattern => 'router.fu.processor.vendor.javapoet'
            ant.relocation :pattern => 'com.google', :shadedPattern => 'router.fu.processor.vendor.google'
          end
        end
        FileUtils.mv shaded_jar, f.to_s
      end
    end

    test.using :testng
    test.options[:properties] = { 'router-fu.fixture_dir' => _('src/test/resources') }
    test.compile.with TEST_DEPS

    # The generators are configured to generate to here.
    iml.test_source_directories << _('generated/processors/test/java')

    iml.test_source_directories << _('src/test/resources/input')
    iml.test_source_directories << _('src/test/resources/expected')
    iml.test_source_directories << _('src/test/resources/bad_input')
  end

  define 'example' do
    pom.provided_dependencies.concat PROVIDED_DEPS

    compile.with project('annotations').package(:jar),
                 project('annotations').compile.dependencies,
                 project('core').package(:jar),
                 project('core').compile.dependencies,
                 project('processor').package(:jar),
                 :arez_annotations,
                 :arez_core,
                 :arez_processor,
                 :arez_component,
                 :javapoet,
                 :guava,
                 :gwt_user

    test.options[:properties] = TEST_OPTIONS
    test.options[:java_args] = ['-ea']

    gwt_enhance(project, :modules_complete => true, :package_jars => false)

    test.using :testng
    test.compile.with TEST_DEPS

    # The generators are configured to generate to here.
    iml.main_generated_source_directories << _('generated/processors/main/java')
  end

  doc.from(projects(%w(annotations core))).
    using(:javadoc,
          :windowtitle => 'RouterFu API Documentation',
          :linksource => true,
          :link => %w(https://arez.github.io/arez/api https://docs.oracle.com/javase/8/docs/api http://www.gwtproject.org/javadoc/latest/),
          :group => {
            'Core Packages' => 'router.fu.*',
            'Annotation Packages' => 'router.fu.annotations*:router.fu.processor*'
          }
    )

  iml.excluded_directories << project._('tmp')

  ipr.add_default_testng_configuration(:jvm_args => '-ea -Dbraincheck.environment=development -Drouter-fu.output_fixture_data=false -Drouter-fu.fixture_dir=processor/src/test/resources')
  ipr.add_component_from_artifact(:idea_codestyle)

  ipr.add_gwt_configuration(project('example'),
                            :gwt_module => 'router.fu.example.Example',
                            :start_javascript_debugger => false,
                            :vm_parameters => "-Xmx2G -Djava.io.tmpdir=#{_('tmp/gwt')}",
                            :shell_parameters => "-port 8888 -codeServerPort 8889 -bindAddress 0.0.0.0 -war #{_(:generated, 'gwt-export')}/")

  ipr.add_component('CompilerConfiguration') do |component|
    component.annotationProcessing do |xml|
      xml.profile(:default => true, :name => 'Default', :enabled => true) do
        xml.sourceOutputDir :name => 'generated/processors/main/java'
        xml.sourceTestOutputDir :name => 'generated/processors/test/java'
        xml.outputRelativeToContentRoot :value => true
      end
    end
  end
end
