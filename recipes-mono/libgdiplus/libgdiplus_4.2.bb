SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=fe7364dfce9f3689eb6995e7cdd56879"

PACKAGECONFIG ??= "jpeg tiff gif exif"
PACKAGECONFIG[jpeg] = "--with-libjpeg,--without-libjpeg,jpeg"
PACKAGECONFIG[tiff] = "--with-libtiff,--without-libtiff,tiff"
PACKAGECONFIG[gif] = "--with-libgif,--without-libgif,giflib"
PACKAGECONFIG[exif] = "--with-libexif,--without-libexif,libexif"

SRC_URI = "https://github.com/mono/libgdiplus/archive/${PV}.tar.gz \
	   file://01-remove-libjpeg-path.patch \
	"

inherit autotools pkgconfig

DEPENDS =+ "cairo freetype fontconfig libxft libpng"

SRC_URI[md5sum] = "925709982aba701c567850617e2206b1"
SRC_URI[sha256sum] = "98f8a8e58ed22e136c4ac6eaafbc860757f5a97901ecc0ea357e2b6e4cfa2be5"

FILES_${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP_${PN} += "dev-so"

do_install_append() {
# fix pkgconfig .pc file
sed -i -e s#I${STAGING_DIR_HOST}#I#g ${D}${libdir}/pkgconfig/*.pc
}
