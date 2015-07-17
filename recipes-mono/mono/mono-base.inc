DEPENDS = "mono-native"

EXTRA_OECONF += " --disable-mcs-build mono_cv_clang=no "

do_install_append() {
        cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}
