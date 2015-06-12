require mono-4.xx.inc

S = "${WORKDIR}/mono-4.0.2"

DEPENDS = "mono-native"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

do_install_append() {
        cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}

SRC_URI[md5sum] = "24b6dbc02f8b69f7a30d01c962dc0c4d"
SRC_URI[sha256sum] = "29d8d9998cadf97b7e6efe8ced4e8a3457b99f5384de58be6d8592df4146e931"

