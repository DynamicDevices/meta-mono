SRC_URI[md5sum] = "b71d0ba80acb576e29f2b5233ce5d163"
SRC_URI[sha256sum] = "731634b24c89680660fed1efd63a39ec6c052e519ae9a2dce61f875598329366"

S = "${WORKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
