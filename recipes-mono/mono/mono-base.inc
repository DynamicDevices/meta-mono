DEPENDS = "mono-native"

EXTRA_OECONF += " --disable-mcs-build mono_cv_clang=no "

MONOLIBS = "\
    2.0 \
    2.0-api \
    3.5 \
    3.5-api \
    4.0 \
    4.0-api \
    4.5 \
    4.5.1-api \
    4.5.2-api \
    4.5-api \
    4.6.1-api \
    4.6.2-api \
    4.6-api \
    4.7-api \
    compat-2.0 \
    gac \
    lldb \
    mono-configuration-crypto \
    monodoc \
    xbuild \
    xbuild-frameworks \
    "

do_install_append() {
        cp -af --no-preserve=ownership \
            ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        install -d ${D}${libdir}/${PN}
        for lib in ${MONOLIBS}; do
            if [ -d "${STAGING_DIR_NATIVE}${libdir}/${PN}/$lib" ]; then
                cp -af --no-preserve=ownership \
                    ${STAGING_DIR_NATIVE}${libdir}/${PN}/$lib ${D}${libdir}/${PN}
            fi
        done
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}
}
