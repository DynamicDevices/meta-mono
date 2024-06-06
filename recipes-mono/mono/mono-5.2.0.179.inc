SRC_URI[sha256sum] = "b5325302000ae992221e46ad9d5d8393aae92a5db7fbef7993d958805d307257"

S = "${WORKDIR}/mono-${PV}"

EXTRA_OECONF = "--disable-btls"
