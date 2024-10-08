SRC_URI[sha256sum] = "f3e2c071e1bd5336b529a9e2a24d3bf348b7f54dcb7e6bee9a22d8abe42fce47"

S = "${UNPACKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
