require mono-4.xx.inc

DEPENDS = "mono-native"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

do_install_append() {
        cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}

SRC_URI[md5sum] = "426065ce1aba25fd3e776ea736c5f430"
SRC_URI[sha256sum] = "ff1f15f3b8d43c6a2818c00fabe377b2d8408ad14acd9d507658b4cae00f5bce"
