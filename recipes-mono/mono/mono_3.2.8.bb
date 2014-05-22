require mono-${PV}.inc

inherit pkgconfig

DEPENDS =+ "mono-native libgdiplus"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

do_install_append() {
	mkdir -p ${D}/etc/
	mkdir -p ${D}/usr/lib/
	cp -af ${STAGING_DIR_NATIVE}/etc/mono ${D}/etc/
	cp -af ${STAGING_DIR_NATIVE}/usr/lib/mono  ${D}/usr/lib/
	# AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
	find ${D}/usr/lib/ -name *.dll.so -o -name *.exe.so | xargs -i rm {} 
        # AJL - Remove extraneous files (might want to package these elsewhere in future?)
        rm -Rf ${D}/usr/share/mono-2.0
        rm -Rf ${D}/usr/share/libgc-mono
}

FILES_${PN} += "${libdir}/libikvm-native.so"
FILES_${PN} += "${libdir}/libMonoPosixHelper.so"
FILES_${PN} += "${libdir}/libMonoSupportW.so"

INSANE_SKIP_${PN} = "arch dev-so debug-files"

RDEPENDS_${PN} += "libgdiplus"
