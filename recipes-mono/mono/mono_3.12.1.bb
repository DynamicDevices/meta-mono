require mono-3.12.inc

inherit pkgconfig

DEPENDS =+ "mono-native"

SRC_URI[md5sum] = "ccab015f0c54ffeccd2924b44885809c"
SRC_URI[sha256sum] = "5d8cf153af2948c06bc9fbf5088f6834868e4db8e5f41c7cff76da173732b60d"

EXTRA_OECONF += "--disable-mcs-build mono_cv_clang=no mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

do_install_append() {
	cp -af ${STAGING_DIR_NATIVE}${sysconfdir}/${PN} ${D}${sysconfdir}
	cp -af ${STAGING_DIR_NATIVE}${libdir}/${PN}  ${D}${libdir}/${PN}
	# AJL - Remove mscorlib.dll.so and mcs.exe.so files copied from mono-native to the mono destination
	find ${D}${libdir}/${PN} -name *.dll.so -o -name *.exe.so | xargs -i rm {} 
}

EDEPENDS_X11 = "libgdiplus"
ERDEPENDS_X11 = "libgdiplus"
EDEPENDS_PROFILE45 = ""
ERDEPENDS_PROFILE45 = "${PN}-libs-4.5"
EDEPENDS_GAC = ""
ERDEPENDS_GAC = "${PN}-gac"
EDEPENDS_LIBS = ""
ERDEPENDS_LIBS = "${PN}-libs"

PACKAGECONFIG[x11] = ",,${EDEPENDS_X11},${ERDEPENDS_X11}"
PACKAGECONFIG[profile45] = ",,${EDEPENDS_PROFILE45},${ERDEPENDS_PROFILE45}"
PACKAGECONFIG[gac] = ",,${EDEPENDS_GAC},${ERDEPENDS_GAC}"
PACKAGECONFIG[libs] = ",,${EDEPENDS_LIBS},${ERDEPENDS_LIBS}"

# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} profile45 gac libs"

PACKAGES = "${PN}-dbg ${PN}-doc ${PN}-staticdev ${PN}-locale ${PN}-libs ${PN}-dev "
PACKAGES += "${PN}-config ${PN}-binaries ${PN}-libs-core "
PACKAGES += "${PN}-libs-2.0 ${PN}-libs-3.5 ${PN}-libs-4.0 ${PN}-libs-4.5 "
PACKAGES += "${PN}-libs-compat-2.0 ${PN}-gac ${PN}-configuration-crypto "
PACKAGES += "${PN}-libs-monodoc ${PN}-xbuild "
PACKAGES += "${PN} "

FILES_${PN}	   			= "${sysconfdir}/* ${bindir}/* ${libdir}/*.so*"
FILES_${PN}-libs   			= "${libdir}/libMono*.so"
FILES_${PN}-libs-2.0 			= "${libdir}/mono/2.0/*"
FILES_${PN}-libs-3.5 			= "${libdir}/mono/3.5/*"
FILES_${PN}-libs-4.0 			= "${libdir}/mono/4.0/*"
FILES_${PN}-libs-4.5 			= "${libdir}/mono/4.5/*"
FILES_${PN}-libs-compat-2.0 		= "${libdir}/mono/compat-2.0/*"
FILES_${PN}-gac				= "${libdir}/mono/gac/*"
FILES_${PN}-configuration-crypto 	= "${libdir}/mono/mono-configuration-crypto/*/*"
FILES_${PN}-libs-monodoc 		= "${libdir}/mono/monodoc/*"
FILES_${PN}-xbuild 			= "${libdir}/mono/xbuild/* ${libdir}/mono/xbuild-frameworks/* ${libdir}/mono/xbuild-frameworks/.NETFramework/*/*/*"
FILES_${PN}-doc 			+= " ${datadir}/libgc-mono/*"
FILES_${PN}-dbg 			+= " ${datadir}/mono-2.0/mono/cil/cil-opcodes.xml ${libdir}/mono/*/*.mdb ${libdir}/mono/gac/*/*/*.mdb"
FILES_${PN}-staticdev 			+= " ${libdir}/*.a"

RDEPENDS_${PN} =+ "bash" 
