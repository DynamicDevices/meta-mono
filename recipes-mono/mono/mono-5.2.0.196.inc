SRC_URI[sha256sum] = "731634b24c89680660fed1efd63a39ec6c052e519ae9a2dce61f875598329366"

S = "${WORKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
