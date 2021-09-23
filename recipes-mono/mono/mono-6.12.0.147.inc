BASEPV = "6.12.0.122"
SRC_URI[sha256sum] = "29c277660fc5e7513107aee1cbf8c5057c9370a4cdfeda2fc781be6986d89d23"

S = "${WORKDIR}/mono-${BASEPV}"

DEPENDS += " cmake-native"

do_configure:prepend () {
	sed -i 's/${BASEPV}/${PV}/g' configure.ac
}
