SRC_URI[sha256sum] = "48d6ae71d593cd01bf0f499de569359d45856cda325575e1bacb5fabaa7e9718"

S = "${UNPACKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
