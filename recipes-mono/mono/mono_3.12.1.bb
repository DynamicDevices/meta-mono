require mono-3.12.inc

inherit pkgconfig

DEPENDS =+ "mono-native libgdiplus"

SRC_URI[md5sum] = "ccab015f0c54ffeccd2924b44885809c"
SRC_URI[sha256sum] = "5d8cf153af2948c06bc9fbf5088f6834868e4db8e5f41c7cff76da173732b60d"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

do_install_append() {
	cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
	cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}/${PN}
	# AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
	find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {} 
}

FILES_${PN} += " ${libdir}/libikvm-native.so"
FILES_${PN} += " ${libdir}/libMonoPosixHelper.so"
FILES_${PN} += " ${libdir}/libMonoSupportW.so"
FILES_${PN}-doc += " ${datadir}/libgc-mono/*"
FILES_${PN}-dbg += " ${datadir}/mono-2.0/mono/cil/cil-opcodes.xml"

PACKAGES = "${PN} ${PN}-dbg ${PN}-doc ${PN}-dev ${PN}-staticdev ${PN}-locale"

RDEPENDS_${PN} += "libgdiplus"
