require 'buildr/git_auto_version'
require 'buildr/gpg'
require 'buildr/single_intermediate_layout'
require 'buildr/gwt'
require 'buildr/shade'

Buildr::MavenCentral.define_publish_tasks(:profile_name => 'org.realityforge', :username => 'realityforge')

FORMATTER_JDK_EXPORTS =
  %w(
    --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
    --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
  )
FORMATTER_JAVAC_OPTIONS = FORMATTER_JDK_EXPORTS.map { |arg| "-J#{arg}" }

desc 'router-fu: A GWT based state router'
define 'router-fu' do
  project.group = 'org.realityforge.router.fu'
  compile.options.source = '17'
  compile.options.target = '17'
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
                 :akasha,
                 :braincheck,
                 :jsinterop_base,
                 :jsinterop_annotations,
                 :grim_annotations,
                 :jetbrains_annotations

    deps = artifacts(:javax_annotation,
                     :braincheck,
                     :akasha)
    pom.include_transitive_dependencies << deps
    pom.dependency_filter = Proc.new { |dep| deps.include?(dep[:artifact]) }
    compile.with deps,
                 :jsinterop_base,
                 :jsinterop_annotations,
                 :grim_annotations,
                 :jetbrains_annotations

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
    pom.dependency_filter = Proc.new { |_| false }

    compile.with :proton_core,
                 :javapoet,
                 :javax_annotation

    test.with :proton_qa,
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
                            'com.palantir.javapoet' => 'router.fu.processor.vendor.javapoet',
                            'org.realityforge.proton' => 'router.fu.processor.vendor.proton')
      end
    end

    test.using :testng
    test.options[:properties] = { 'router.fu.fixture_dir' => _('src/test/fixtures') }
    test.options[:java_args] = ['-ea'] + FORMATTER_JDK_EXPORTS
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

    compile.options[:processor] = true
    compile.options.other += FORMATTER_JAVAC_OPTIONS

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

  ipr.add_default_testng_configuration(:jvm_args => "-ea #{FORMATTER_JDK_EXPORTS.join(' ')} -Dbraincheck.environment=development -Drouter.fu.output_fixture_data=false -Drouter.fu.fixture_dir=processor/src/test/fixtures")

  ipr.add_gwt_configuration(project('example'),
                            :gwt_module => 'router.fu.example.Example',
                            :start_javascript_debugger => false,
                            :vm_parameters => '-Xmx2G',
                            :shell_parameters => "-strict -style PRETTY -XmethodNameDisplayMode FULL -nostartServer -incremental -codeServerPort 8889 -bindAddress 0.0.0.0 -deploy #{_(:generated, :gwt, 'deploy')} -extra #{_(:generated, :gwt, 'extra')} -war #{_(:generated, :gwt, 'war')}")

  ipr.add_code_insight_settings
  ipr.add_nullable_manager
  ipr.add_javac_settings('-Xlint:all,-processing,-serial -Werror -Xmaxerrs 10000 -Xmaxwarns 10000')
end
