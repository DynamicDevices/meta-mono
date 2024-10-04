SRC_URI[sha256sum] = "03b8e463032bc425673dec844b35b4c669f5b99b0e45521195efb3741a9f5e94"

S = "${UNPACKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
