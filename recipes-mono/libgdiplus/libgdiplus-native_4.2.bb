SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fe7364dfce9f3689eb6995e7cdd56879"

SRC_URI = "https://github.com/mono/libgdiplus/archive/${PV}.tar.gz \
	"

inherit autotools pkgconfig native

DEPENDS =+ "cairo-native freetype-native fontconfig-native libxft-native libpng-native pango-native giflib-native"

SRC_URI[md5sum] = "925709982aba701c567850617e2206b1"
SRC_URI[sha256sum] = "98f8a8e58ed22e136c4ac6eaafbc860757f5a97901ecc0ea357e2b6e4cfa2be5"

FILES_${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP_${PN} += "dev-so"

