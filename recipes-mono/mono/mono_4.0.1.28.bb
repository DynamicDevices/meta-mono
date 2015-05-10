require mono-4.xx.inc

DEPENDS = "mono-native"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

S = "${WORKDIR}/${PN}-4.0.1"

do_install_append() {
        cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}

SRC_URI[md5sum] = "2e71b347e23408548349492f75bfd2ca"
SRC_URI[sha256sum] = "31f7da2b2c3b39e4b3c549c0fef29604b22e827ac46cf6aa454bd5b22e4f2ec5"
