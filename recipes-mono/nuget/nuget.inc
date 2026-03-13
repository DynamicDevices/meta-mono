SUMMARY = "Package manager for .Net/Mono development platform"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://Apache-LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57"
HOMEPAGE = "http://nuget.org/"

# This package ships Mono EXE and a shell script
PACKAGE_ARCH = "all"

# Fully updated Fedora 33 and 34 say that dist.nuget.org cert is untrusted
BB_CHECK_SSL_CERTS = "0"

inherit mono

SRC_URI = " \
			http://dist.nuget.org/win-x86-commandline/v${PV}/nuget.exe;downloadfilename=NuGet-v${PV}.exe \
			http://www.apache.org/licenses/LICENSE-2.0.txt;downloadfilename=Apache-LICENSE-2.0.txt;name=lic \
			file://nuget.sh.in \
		"

SRC_URI[lic.sha256sum] = "cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30"

S = "${UNPACKDIR}"

do_install () {
	install -d -m0755 ${D}${bindir}
	install -m0755 ${S}/nuget.sh.in ${D}${bindir}/nuget
	sed -i -e 's:@bindir@:${bindir}:g' -e 's:@libdir@:${libdir}:g' ${D}${bindir}/nuget

	install -d -m0755 ${D}${libdir}/mono/nuget
	install -m0755 ${S}/NuGet-v${PV}.exe ${D}${libdir}/mono/nuget/NuGet.exe
}

FILES:${PN} += "${libdir}/mono/nuget"

SYSROOT_PREPROCESS_FUNCS += "nuget_sysroot_preprocess" 

nuget_sysroot_preprocess () {
	install -d -m0755 ${SYSROOT_DESTDIR}${bindir_crossscripts}/
	install -m0755 ${S}/nuget.sh.in ${SYSROOT_DESTDIR}${bindir_crossscripts}/nuget
	sed -i -e 's:@bindir@:${STAGING_BINDIR_NATIVE}:g' -e 's:@libdir@:${STAGING_LIBDIR}:g' ${SYSROOT_DESTDIR}${bindir_crossscripts}/nuget
}

DIRFILES = "1"

BBCLASSEXTEND = "native"
