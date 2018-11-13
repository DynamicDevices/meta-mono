SRC_URI[md5sum] = "76b4f7187287ff49832d38446fe692f9"
SRC_URI[sha256sum] = "93839af2ef0b8796935d8c1efd4cc693bc294bb2beb657b9edb6b4764f01e12b"

SRC_URI += " file://shm_open-test-crosscompile.diff "

S = "${WORKDIR}/mono-${PV}"
