"""Checked J2CL packages and internal views of their existing Java outputs."""

load("@j2cl//build_defs:rules.bzl", "j2cl_library")
load("@j2cl//build_defs/internal_do_not_use:provider.bzl", "J2clInfo")
load("@rules_java//java/common:java_info.bzl", "JavaInfo")
load("//third_party/java:rules.bzl", "J2CL_JAVACOPTS")

visibility(["//..."])

def _java_outputs_impl(ctx):
    info = ctx.attr.library[J2clInfo]._private_.java_info
    output = info.java_outputs[0]
    return [
        JavaInfo(
            output_jar = output.class_jar,
            compile_jar = output.compile_jar,
            source_jar = info.source_jars[0],
            generated_class_jar = output.generated_class_jar,
            generated_source_jar = output.generated_source_jar,
        ),
        DefaultInfo(files = depset([output.class_jar])),
    ]

_java_outputs = rule(
    implementation = _java_outputs_impl,
    attrs = {
        "library": attr.label(mandatory = True, providers = [J2clInfo]),
        "srcs": attr.label_list(allow_files = [".java"]),
    },
)

def java_j2cl_library(name, srcs, deps = [], plugins = [], testonly = False, visibility = None, java_visibility = None):
    for src in srcs:
        if "/" in src or src.startswith("@"):
            fail("java_j2cl_library sources must be files in the current package: " + src)
    j2cl_library(
        name = name,
        testonly = testonly,
        visibility = visibility,
        generate_j2wasm_library = False,
        srcs = srcs,
        javacopts = J2CL_JAVACOPTS,
        plugins = ["//third_party/java:nullaway_plugin"] + plugins,
        experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
        deps = ["//third_party/java:jspecify-j2cl"] + deps,
    )
    _java_outputs(
        name = name + "_java",
        testonly = testonly,
        library = ":" + name,
        srcs = srcs,
        visibility = java_visibility,
    )
