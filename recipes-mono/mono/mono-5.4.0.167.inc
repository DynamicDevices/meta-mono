SRC_URI[sha256sum] = "c2afe51b0fb074936a8e7eaee805c352f37cbf1093bb41c5345078f77d913ce0"

S = "${WORKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
