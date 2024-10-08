DEPENDS += "mono-native"

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
    4.7.1-api \
    4.7.2-api \
    4.8-api \
    compat-2.0 \
    gac \
    lldb \
    mono-configuration-crypto \
    monodoc \
    xbuild \
    xbuild-frameworks \
    "

do_compile() {
    oe_runmake \
        BUILD_MAPFILE="/pathmap:${S}/mcs/class/lib/net_4_x-linux=.,${S}/mcs/class/lib/net_4_x-linux/Facades=.,${S}/mcs/class/lib/xbuild_12=.,${S}/mcs/class/lib/xbuild_14=.,${S}/mcs/tools/security=."
}

do_install:append() {
        install -d ${D}${libdir}/${PN}
        for lib in ${MONOLIBS}; do
            if [ -d "${STAGING_DIR_NATIVE}${libdir}/${PN}/$lib" ]; then
                cp -af --no-preserve=ownership \
                    ${STAGING_DIR_NATIVE}${libdir}/${PN}/$lib ${D}${libdir}/${PN}
            fi
        done
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}

        sed -i 's|${STAGING_LIBDIR_NATIVE}|${libdir}|g' ${D}${libdir}/mono/gac/monodoc/*/monodoc.dll.config
        rm -f ${D}${libdir}/mono/xbuild/Microsoft/NuGet/Microsoft.NuGet.Build.Tasks.pdb
}
