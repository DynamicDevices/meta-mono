DEPENDS = "mono-native"

EXTRA_OECONF += " --disable-mcs-build mono_cv_clang=no "

do_install_append() {
        cp -af --no-preserve=ownership \
            ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
        cp -af --no-preserve=ownership \
            ${STAGING_DIR_NATIVE}${libdir}/${PN} ${D}${libdir}
        # AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
        find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {}

        # Libhostfxr has its own package and it is not part of basic mono, although it has to be copied to msbuild directory
        # in order to make it working. Here we will delete the file in order not to include it twice to the install image.
        # This also prevents already-stripped QA issue (the file is stripped using msbuild-libhostfxr recipe).
        rm -f ${D}${libdir}/mono/msbuild/Current/bin/SdkResolvers/Microsoft.DotNet.MSBuildSdkResolver/libhostfxr.so
}
