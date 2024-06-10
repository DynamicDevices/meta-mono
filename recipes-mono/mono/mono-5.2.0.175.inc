SRC_URI[sha256sum] = "cd00cc9d582449853b861419fa5647c0c2174e7fc07b2aeb03ba5ca847da4b18"

S = "${WORKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
