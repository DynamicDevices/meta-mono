SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

SRCBRANCH = "master"
SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/mono/mono.git;branch=${SRCBRANCH}\
           file://dllmap-config.in.diff \
	   file://0002-prevent-threadpool-exception5-test-hanging.patch \
"

# Add this patch into SRC_URI when testing projects
# that fail to compile due to lack of gmcs in recent
# mono releases
#
#	   file://0001-reintroduce-gmcs.patch \
#

S = "${WORKDIR}/git"

FILESPATH =. "${FILE_DIRNAME}/mono-4.xx:"
FILESPATH =. "${FILE_DIRNAME}/mono-${PV}:"

inherit autotools-brokensep
inherit pkgconfig

EXTRA_OECONF += " mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
    sed -e "s|slash\}libtool|slash\}${HOST_SYS}-libtool|" -i acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i libgc/acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i eglib/acinclude.m4
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/mono-wrapper.in
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/monodis-wrapper.in
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
PACKAGES += "${PN}-lldb "
PACKAGES += "${PN}-profiler "
PACKAGES += "${PN} "

FILES_${PN}	   			= "${sysconfdir}/* ${bindir}/* ${libdir}/*.so*"
FILES_${PN}-libs   			= "${libdir}/libMono*.so"
FILES_${PN}-lldb                        = "${libdir}/mono/lldb/*"
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
FILES_${PN}-profiler 			+= " ${datadir}/mono-2.0/mono/profiler/*"

RDEPENDS_${PN} =+ "bash" 

# Workaround for observed race in `make install`
PARALLEL_MAKEINST=""
