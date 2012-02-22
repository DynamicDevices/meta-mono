SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fe7364dfce9f3689eb6995e7cdd56879"

SRC_URI = "http://download.mono-project.com/sources/libgdiplus/libgdiplus-${PV}.tar.bz2 \
           file://libgdiplus-2.10/cairo/configure.in.diff"

inherit autotools pkgconfig

DEPENDS =+ "cairo freetype fontconfig libxft libpng"

PR = "r0"

SRC_URI[md5sum] = "451966e8f637e3a1f02d1d30f900255d"
SRC_URI[sha256sum] = "2d9e0397ef5583ed855eaafcdac8cabbe1d58463ed1e4b545be6dde5b63712a4"


FILES_${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP_${PN} += "dev-so"

