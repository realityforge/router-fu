require 'buildr/git_auto_version'
require 'buildr/gpg'
require 'buildr/single_intermediate_layout'

PROVIDED_DEPS = [:javax_jsr305, :jetbrains_annotations, :anodoc]
TEST_DEPS = [:mockito, :guiceyloops]

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

  iml.excluded_directories << project._('tmp')

  ipr.add_default_testng_configuration(:jvm_args => '-ea -Dbraincheck.environment=development')
  ipr.add_component_from_artifact(:idea_codestyle)

  ipr.add_gwt_configuration(project('core'),
                            :gwt_module => 'router.fu.RouterFuDev',
                            :start_javascript_debugger => false,
                            :vm_parameters => "-Xmx2G -Djava.io.tmpdir=#{_('tmp/gwt')}",
                            :shell_parameters => "-port 8888 -codeServerPort 8889 -bindAddress 0.0.0.0 -war #{_(:generated, 'gwt-export')}/")
end
