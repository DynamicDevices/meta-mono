SUMMARY = "C-based implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "https://github.com/mono/libgdiplus/issues"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=e0a7dacaa67d7e24a32074fba736dc59"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} jpeg tiff gif exif pango"
PACKAGECONFIG[jpeg] = ",--with-libjpeg=no,jpeg"
PACKAGECONFIG[tiff] = ",--with-libtiff=no,tiff"
PACKAGECONFIG[gif] = ",--with-libgif-no,giflib"
PACKAGECONFIG[exif] = ",--without-libexif,libexif"
PACKAGECONFIG[pango] = "--with-pango,,pango"
PACKAGECONFIG[x11] = ",--without-x11,libx11"

SRC_URI = "https://github.com/mono/libgdiplus/archive/${PV}.tar.gz \
           file://0001-fix-cross-compile.patch"

inherit autotools pkgconfig

DEPENDS =+ "cairo freetype fontconfig libxft libpng"

SRC_URI[md5sum] = "94f9f05004e6054e414d747844e897b0"
SRC_URI[sha256sum] = "d605bf548affd29bd0418001ffb1bb8c1bf9962c1c37c23744abb0194a099232"

FILES_${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP_${PN} += "dev-so"

do_install_append() {
# fix pkgconfig .pc file
sed -i -e s#I${STAGING_DIR_HOST}#I#g ${D}${libdir}/pkgconfig/*.pc
}
