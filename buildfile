require 'buildr/git_auto_version'
require 'buildr/gpg'
require 'buildr/single_intermediate_layout'
require 'buildr/gwt'
require 'buildr/jacoco'
require 'buildr/shade'

Buildr::MavenCentral.define_publish_tasks(:profile_name => 'org.realityforge', :username => 'realityforge')

desc 'router-fu: A GWT based state router'
define 'router-fu' do
  project.group = 'org.realityforge.router.fu'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all,-processing,-serial'
  project.compile.options.warnings = true
  project.compile.options.other = %w(-Werror -Xmaxerrs 10000 -Xmaxwarns 10000)

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  pom.add_apache_v2_license
  pom.add_github_project('realityforge/router-fu')
  pom.add_developer('realityforge', 'Peter Donald')

  desc 'The core router-fu code'
  define 'core' do
    compile.with :javax_annotation,
                 :jsinterop_base,
                 :jsinterop_annotations,
                 :grim_annotations,
                 :jetbrains_annotations,
                 :akasha,
                 :braincheck

    compile.options[:processor_path] << artifacts(:grim_processor, :javax_json)

    test.options[:properties] = { 'braincheck.environment' => 'development' }
    test.options[:java_args] = ['-ea']

    gwt_enhance(project)

    package(:jar)
    package(:sources)
    package(:javadoc)

    test.using :testng
    test.compile.with :guiceyloops
  end

  desc 'The Annotation processor'
  define 'processor' do
    compile.with :proton_core,
                 :javapoet,
                 :javax_annotation

    test.with :compile_testing,
              Buildr::Util.tools_jar,
              :truth,
              :junit,
              :hamcrest_core,
              :guava,
              :guava_failureaccess,
              :proton_qa,
              :arez_core,
              :arez_processor,
              project('core').package(:jar),
              project('core').compile.dependencies

    package(:jar)
    package(:sources)
    package(:javadoc)

    package(:jar).enhance do |jar|
      jar.merge(artifact(:javapoet))
      jar.merge(artifact(:proton_core))
      jar.enhance do |f|
        Buildr::Shade.shade(f,
                            f,
                            'com.squareup.javapoet' => 'router.fu.processor.vendor.javapoet',
                            'org.realityforge.proton' => 'router.fu.processor.vendor.proton')
      end
    end

    test.using :testng
    test.options[:properties] = { 'router.fu.fixture_dir' => _('src/test/fixtures') }
    test.compile.with :guiceyloops

    # The generators are configured to generate to here.
    iml.test_source_directories << _('generated/processors/test/java')

    iml.test_source_directories << _('src/test/fixtures/input')
    iml.test_source_directories << _('src/test/fixtures/expected')
    iml.test_source_directories << _('src/test/fixtures/bad_input')
  end

  define 'example' do
    compile.with project('core').package(:jar),
                 project('core').compile.dependencies,
                 project('processor').package(:jar),
                 :arez_core,
                 :arez_processor,
                 :javapoet,
                 :guava,
                 :gwt_user

    gwt_enhance(project, :modules_complete => true, :package_jars => false)

    # The generators are configured to generate to here.
    iml.main_generated_source_directories << _('generated/processors/main/java')
  end

  doc.from(projects(%w(core))).
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

  ipr.add_default_testng_configuration(:jvm_args => '-ea -Dbraincheck.environment=development -Drouter.fu.output_fixture_data=false -Drouter.fu.fixture_dir=processor/src/test/resources')

  ipr.add_gwt_configuration(project('example'),
                            :gwt_module => 'router.fu.example.Example',
                            :start_javascript_debugger => false,
                            :vm_parameters => '-Xmx2G',
                            :shell_parameters => "-strict -style PRETTY -XmethodNameDisplayMode FULL -nostartServer -incremental -codeServerPort 8889 -bindAddress 0.0.0.0 -deploy #{_(:generated, :gwt, 'deploy')} -extra #{_(:generated, :gwt, 'extra')} -war #{_(:generated, :gwt, 'war')}")

  ipr.add_component_from_artifact(:idea_codestyle)
  ipr.add_code_insight_settings
  ipr.add_nullable_manager
  ipr.add_javac_settings('-Xlint:all,-processing,-serial -Werror -Xmaxerrs 10000 -Xmaxwarns 10000')
end
