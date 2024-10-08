require mono-4.xx.inc
require mono-gplv2.inc

DEPENDS = "mono-native"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

S = "${UNPACKDIR}/${PN}-4.0.1"

do_install:append() {
        cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}

SRC_URI[sha256sum] = "220125938cb85482a398e11ed90265eecf4df5defb259ade7e30208ecb859c04"

