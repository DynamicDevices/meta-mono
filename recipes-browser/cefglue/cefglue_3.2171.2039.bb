require cefglue.inc

SRC_URI += "file://0002_correct-cef3.2171.2039-hashes.patch"

SRC_URI[md5sum] = "a1f5bcfec3683f45e2fcaa3e9f044455"
SRC_URI[sha256sum] = "6507643b52b400ac07a23188b479d008ce2b52d42c4c0d8ebaf1bed2cefd2fc0"

S = "${WORKDIR}/xilium-xilium.cefglue-4caf9b2bb5b5"
