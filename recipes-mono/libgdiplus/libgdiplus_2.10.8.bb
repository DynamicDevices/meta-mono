SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fe7364dfce9f3689eb6995e7cdd56879"

SRC_URI = "https://github.com/mono/libgdiplus/archive/${PV}.tar.gz \
           file://libgdiplus-2.10/cairo/configure.in.diff \
           file://libgdiplus-2.10/libgdiplus-2.10.1-libpng15.patch \
	   file://libgdiplus-2.10/libgdiplus-2.10.x-use-freetype-include-macro.patch \
	"

inherit autotools pkgconfig

DEPENDS =+ "cairo freetype fontconfig libxft libpng jpeg tiff giflib libexif"

SRC_URI[md5sum] = "6fd45bbb9843f5a8851b5f44e2a5dd04"
SRC_URI[sha256sum] = "45c533dc72af0a24d1d3a8097873f5fe1670107fe7e6d08fb71ae586c87a0f1d"

FILES_${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP_${PN} += "dev-so"

