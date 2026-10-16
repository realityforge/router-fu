"""Validate the local Maven boundary without publishing anything."""
import sys
import zipfile
import xml.etree.ElementTree as ET
from pathlib import Path

root, version = Path(sys.argv[1]), sys.argv[2]
ns = {"m": "http://maven.apache.org/POM/4.0.0"}
for module in ("core", "processor"):
    prefix = f"router-fu-{module}"
    for suffix in (".jar", "-sources.jar", "-javadoc.jar"):
        with zipfile.ZipFile(root / (prefix + suffix)) as archive:
            names = archive.namelist()
            assert len(names) == len(set(names)), (prefix, "duplicate entries")
            assert "META-INF/LICENSE" in names
            assert not any(name.endswith(".gwt.xml") for name in names)
            assert not any(name.startswith(("com/palantir/", "org/realityforge/proton/")) for name in names)
            if suffix == ".jar":
                assert not any(name.endswith(".java") for name in names)
                assert not any(name.startswith(("org/realityforge/braincheck/", "jsinterop/base/")) for name in names)
            elif suffix == "-sources.jar":
                assert any(name.endswith(".java") for name in names)
                assert not any(name.endswith(".class") for name in names)
    pom = ET.parse(root / (prefix + ".pom")).getroot()
    assert pom.findtext("m:groupId", namespaces=ns) == "org.realityforge.router.fu"
    assert pom.findtext("m:artifactId", namespaces=ns) == prefix
    assert pom.findtext("m:version", namespaces=ns) == version
    dependencies = pom.findall("m:dependencies/m:dependency", ns)
    if module == "processor":
        assert not dependencies, "Processor must be self-contained"
    else:
        actual = {d.findtext("m:artifactId", namespaces=ns): d.findtext("m:version", namespaces=ns) for d in dependencies}
        assert actual["base"] == "1.2.0"
        assert actual["jspecify"] == "1.0.1"
        if not version.endswith("-SNAPSHOT"):
            assert not actual["braincheck-core"].endswith("-SNAPSHOT"), "Select a released migrated Braincheck version before publishing"
with zipfile.ZipFile(root / "router-fu-processor.jar") as archive:
    assert archive.read("META-INF/services/javax.annotation.processing.Processor").decode().strip() == "router.fu.processor.RouterProcessor"
    assert "router/fu/processor/vendor/proton/GeneratorUtil.class" in archive.namelist()
    assert "router/fu/processor/vendor/javapoet/TypeName.class" in archive.namelist()
print("Validated both Maven artifacts, source archives, Javadocs, POMs and processor service")
