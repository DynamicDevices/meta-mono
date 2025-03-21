SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"

DEPENDS = "zlib cmake-native"

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.tar.bz2 \
           file://dllmap-config.in.diff \
	   file://0002-prevent-threadpool-exception5-test-hanging.patch \
	   file://0003-include-sys-sysmacros.h-for-makedev.patch \
"

# Add this patch into SRC_URI when testing projects
# that fail to compile due to lack of gmcs in recent
# mono releases
#
#	   file://0001-reintroduce-gmcs.patch \
#

def mono_workspace_version (d):
    pvsplit = d.getVar('PV').split('.')
    if len(pvsplit) == 3:
        return pvsplit[0] + '.' + pvsplit[1] + '.' + pvsplit[2]
    elif len(pvsplit) == 2:
        return pvsplit[0] + '.' + pvsplit[1]
    elif len(pvsplit) == 1:
        return pvsplit[0]
    else:
        return 0

S = "${UNPACKDIR}/mono-${@mono_workspace_version(d)}"

FILESPATH =. "${FILE_DIRNAME}/mono-4.xx:"
FILESPATH =. "${FILE_DIRNAME}/mono-${PV}:"

inherit autotools-brokensep
inherit pkgconfig
inherit gettext

# avoids build breaks when using no-static-libs.inc
DISABLE_STATIC = ""

EXTRA_OECONF += " mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

do_configure:prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
    find . -name "*.pc.in" -exec sed -i -e 's:^\([^=]*=\).*pcfiledir.*/\.\./\.\.:\1@prefix@:g' {} \;
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
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} profile45 gac libs"

PACKAGES = "${PN}-dbg ${PN}-doc ${PN}-staticdev ${PN}-locale ${PN}-libs ${PN}-dev "
PACKAGES += "${PN}-config ${PN}-binaries ${PN}-libs-core "
PACKAGES += "${PN}-libs-2.0 ${PN}-libs-3.5 ${PN}-libs-4.0 ${PN}-libs-4.5 "
PACKAGES += "${PN}-libs-compat-2.0 ${PN}-gac ${PN}-configuration-crypto "
PACKAGES += "${PN}-libs-monodoc ${PN}-xbuild "
PACKAGES += "${PN}-lldb "
PACKAGES += "${PN} "

FILES:${PN}				= "${sysconfdir}/* ${bindir}/* ${libdir}/*.so*"
FILES:${PN}-libs			= "${libdir}/libMono*.so ${libdir}/libikvm-native.so"
FILES:${PN}-lldb                        = "${libdir}/mono/lldb/*"
FILES:${PN}-libs-2.0                    = "${libdir}/mono/2.0/* ${libdir}/mono/2.0-api/*"
FILES:${PN}-libs-3.5                    = "${libdir}/mono/3.5/* ${libdir}/mono/3.5-api/*"
FILES:${PN}-libs-4.0                    = "${libdir}/mono/4.0/* ${libdir}/mono/4.0-api/*"
FILES:${PN}-libs-4.5                    = "${libdir}/mono/4.5/* ${libdir}/mono/4.5-api/*"
FILES:${PN}-libs-compat-2.0		= "${libdir}/mono/compat-2.0/*"
FILES:${PN}-gac				= "${libdir}/mono/gac/*"
FILES:${PN}-configuration-crypto	= "${libdir}/mono/mono-configuration-crypto/*/*"
FILES:${PN}-libs-monodoc		= "${libdir}/mono/monodoc/*"
FILES:${PN}-xbuild			= "${bindir}/xbuild ${libdir}/mono/xbuild/* ${libdir}/mono/xbuild-frameworks/* ${libdir}/mono/xbuild-frameworks/.NETFramework/*/*/* ${libdir}/mono/xbuild-frameworks/.NETPortable/*/*/* ${libdir}/mono/xbuild-frameworks/.NETPortable/*/*/.NET*"
FILES:${PN}-dev                         += " ${bindir}/al ${bindir}/al2 ${bindir}/caspol ${bindir}/cccheck ${bindir}/cert2spc ${bindir}/certmgr ${bindir}/crlupdate ${bindir}/dtd2rng ${bindir}/dtd2xsd ${bindir}/genxs ${bindir}/ilasm ${bindir}/installvst ${bindir}/macpack ${bindir}/makecert ${bindir}/mdbrebase ${bindir}/mkbundle ${bindir}/mono-api-html ${bindir}/mono-api-info ${bindir}/mono-cil-strip ${bindir}/mono-find-provides ${bindir}/mono-find-requires ${bindir}/mono-shlib-cop ${bindir}/mono-symbolicate ${bindir}/mono-xmltool ${bindir}/monodis ${bindir}/monolinker ${bindir}/monop ${bindir}/monop2 ${bindir}/pedump ${bindir}/permview ${bindir}/peverify ${bindir}/resgen ${bindir}/resgen2 ${bindir}/secutil ${bindir}/sgen ${bindir}/signcode"
FILES:${PN}-doc				+= " ${datadir}/libgc-mono/*"
FILES:${PN}-dbg				+= " ${datadir}/mono-2.0/mono/cil/cil-opcodes.xml ${libdir}/mono/*/*.mdb ${libdir}/mono/gac/*/*/*.mdb"
FILES:${PN}-staticdev			+= " ${libdir}/*.a"

RDEPENDS:${PN}-dev =+ "bash"

# Workaround for observed race in `make install`
PARALLEL_MAKEINST=""
