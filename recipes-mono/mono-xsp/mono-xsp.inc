DESCRIPTION = "Standalone web server written in C# that can be used to run ASP.NET applications"
SECTION = "devel/mono"
DEPENDS = "mono"
RDEPENDS:${PN} = "mono"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=84b17f6d63c3c28399d0f3786831d40c"

inherit autotools-brokensep

SRC_URI = "http://download.mono-project.com/sources/xsp/xsp-${PV}.tar.bz2"

S = "${UNPACKDIR}/xsp-${PV}"

PACKAGES += "${PN}-test \
	${PN}-unittest \
"

FILES:${PN}-test = "${libdir}/xsp/test/*"

FILES:${PN}-unittest = "${libdir}/xsp/unittests/*"

FILES:${PN}-doc += "${libdir}/monodoc/*"

# AJL - For now we are going to parcel up the different XSP hosting (mod_mono, fastcgi, xsp) together. More granular packages in future
FILES:${PN} = "${bindir}/* \
	${libdir}/mono/* \
	${libdir}/xsp/2.0/* \
	${libdir}/xsp/4.0/* \
"

# We seem to run into problems with make parallelisation building mono-xsp, so disable it for now
PARALLEL_MAKE = ""

