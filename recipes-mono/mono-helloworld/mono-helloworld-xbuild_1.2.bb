require mono-helloworld.inc

SRC_URI[md5sum] = "ae22f282d36ae5cb82ae5a2e9bcbb8b5"
SRC_URI[sha256sum] = "365360d674bd63ab7ca1762e64e3d5d6c6d4841edf6e59f67ff8b40fafeb1137"

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
