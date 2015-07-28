require mono-helloworld.inc

SRC_URI[md5sum] = "79b0ba0044689789a54e3d55ec400fc0"
SRC_URI[sha256sum] = "56388435f29ce94007155acc39593c900b6d3248a7f281e83ed2101a6da455f0"

REALPN = "mono-helloworld"

S = "${WORKDIR}/${REALPN}-${PV}"

CONFIGURATION = "Debug"


do_compile() {
        xbuild /p:Configuration=${CONFIGURATION} ${REALPN}_vs2010.sln
}

do_install() {
        install -d "${D}${bindir}"
	install -d "${D}${libdir}/helloworld/.debug"
        install -m 0755 ${S}/bin/${CONFIGURATION}/*.mdb ${D}${libdir}/helloworld/.debug
        install -m 0755 ${S}/bin/${CONFIGURATION}/*.exe ${D}${libdir}/helloworld

        install -m 0755 ${S}/script.in ${D}${bindir}/helloworld
        sed -i "s|@MONO@|mono|g" ${D}${bindir}/helloworld
        sed -i "s|@prefix@|/usr|g" ${D}${bindir}/helloworld
        sed -i "s|@APP@|helloworld|g" ${D}${bindir}/helloworld
        install -m 0755 ${S}/script.in ${D}${bindir}/helloworldform
        sed -i "s|@MONO@|mono|g" ${D}${bindir}/helloworldform
        sed -i "s|@prefix@|/usr|g" ${D}${bindir}/helloworldform
        sed -i "s|@APP@|helloworld|g" ${D}${bindir}/helloworldform
}

FILES_${PN}-dbg += "${libdir}/helloworld/.debug/*"
