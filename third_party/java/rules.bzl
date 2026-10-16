load("@rules_java//java:defs.bzl", _java_binary = "java_binary", _java_library = "java_library", _java_plugin = "java_plugin", _java_test = "java_test")

# Share the Java 17 and nullness policy between owned code and fixture compilations.
_JAVACOPTS = [
    "--release",
    "17",
    "-Werror",
    "-Xlint:all,-processing,-serial,-this-escape",
    "-Xmaxerrs",
    "10000",
    "-Xmaxwarns",
    "10000",
]
_ERROR_PRONE_JAVACOPTS = ["-Xep:AnnotationPosition:ERROR"]

_NULLAWAY_JAVACOPTS = [
    "-XDaddTypeAnnotationsToSymbol=true",
    "-Xep:NullAway:ERROR",
    "-Xep:RequireExplicitNullMarking:ERROR",
    "-Xep:JSpecifyUnrecognizedAnnotationLocation:ERROR",
    "-XepOpt:NullAway:OnlyNullMarked=true",
    "-XepOpt:NullAway:JSpecifyMode=true",
    "-XepOpt:NullAway:AssertsEnabled=true",
    "-XepOpt:NullAway:CastToNonNullMethod=java.util.Objects.requireNonNull",
]

FORMATTER_JVM_FLAGS = [
    "--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
    "--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
]

def _compile_policy(srcs, javacopts, deps, plugins):
    options = _JAVACOPTS + _ERROR_PRONE_JAVACOPTS
    if srcs:
        options += _NULLAWAY_JAVACOPTS
        if "//third_party/java:jspecify" not in deps:
            deps = ["//third_party/java:jspecify"] + deps
        if "//third_party/java:nullaway_plugin" not in plugins:
            plugins = ["//third_party/java:nullaway_plugin"] + plugins
    return (options + javacopts, deps, plugins)

def java_library(name, srcs = [], javacopts = [], deps = [], plugins = [], **kwargs):
    options, deps, plugins = _compile_policy(srcs, javacopts, deps, plugins)
    _java_library(name = name, srcs = srcs, javacopts = options, deps = deps, plugins = plugins, **kwargs)

def java_plugin(name, srcs = [], javacopts = [], deps = [], plugins = [], **kwargs):
    options, deps, plugins = _compile_policy(srcs, javacopts, deps, plugins)
    _java_plugin(name = name, srcs = srcs, javacopts = options, deps = deps, plugins = plugins, **kwargs)

def java_binary(name, srcs = [], javacopts = [], deps = [], plugins = [], **kwargs):
    options, deps, plugins = _compile_policy(srcs, javacopts, deps, plugins)
    _java_binary(name = name, srcs = srcs, javacopts = options, deps = deps, plugins = plugins, exclude_build_data = True, **kwargs)

def java_test(name, srcs = [], javacopts = [], deps = [], plugins = [], jvm_flags = [], **kwargs):
    options, deps, plugins = _compile_policy(srcs, javacopts, deps, plugins)
    _java_test(name = name, srcs = srcs, javacopts = options, deps = deps, plugins = plugins, exclude_build_data = True, jvm_flags = ["-ea"] + jvm_flags, **kwargs)

def java_testng(name, srcs, test_classes = [], test_class = None, test_methods = [], deps = [], runtime_deps = [], jvm_flags = [], javacopts = [], **kwargs):
    if test_class and test_classes:
        fail("Specify only one of test_class or test_classes")
    if test_class:
        test_classes = [test_class]
    if not test_classes:
        fail("Specify test_class or test_classes")
    java_test(
        name = name,
        srcs = srcs,
        use_testrunner = False,
        main_class = "org.testng.TestNG",
        args = ["-testclass", ",".join(test_classes), "-verbose", "2"] + (["-methods", ",".join(test_methods)] if test_methods else []),
        deps = ["//third_party/java:testng"] + deps if srcs else [],
        runtime_deps = runtime_deps + (["//third_party/java:testng"] + deps if not srcs else []),
        javacopts = javacopts,
        jvm_flags = jvm_flags,
        **kwargs
    )

def _checker_options_impl(ctx):
    output = ctx.actions.declare_file(ctx.label.name + ".txt")
    ctx.actions.write(output, "\n".join(_JAVACOPTS + _ERROR_PRONE_JAVACOPTS + _NULLAWAY_JAVACOPTS) + "\n")
    return [DefaultInfo(files = depset([output]))]

strict_checker_options = rule(implementation = _checker_options_impl)
