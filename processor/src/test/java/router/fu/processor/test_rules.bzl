load("//third_party/java:rules.bzl", "FORMATTER_JVM_FLAGS", "java_testng")

FIXTURES = "//processor/src/test/resources/fixtures"
MAIN = "router.fu.processor.RouterProcessorTest"

def feature(name, trees = ["input", "expected", "expectedFormatted"]):
    return ["%s/%s/com/example/%s:all" % (FIXTURES, tree, name) for tree in trees]

def processor_test(name, data, methods):
    java_testng(
        name = name,
        srcs = [],
        data = data + ["//third_party/java:strict_checker_options"],
        jvm_flags = FORMATTER_JVM_FLAGS + [
            "-Drouter.fu.fixture_dir=processor/src/test/resources/fixtures",
            "--add-exports=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED",
            "--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
            "--add-exports=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
            "--add-exports=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
            "--add-exports=jdk.compiler/com.sun.tools.javac.jvm=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED",
        ],
        test_class = MAIN,
        test_methods = [MAIN + "." + method for method in methods],
        deps = [":test_classes"],
    )
