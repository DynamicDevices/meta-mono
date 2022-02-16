PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)} jpeg tiff gif exif"
PACKAGECONFIG[jpeg] = "--with-libjpeg,--without-libjpeg,jpeg"
PACKAGECONFIG[tiff] = "--with-libtiff,--without-libtiff,tiff"
PACKAGECONFIG[gif] = "--with-libgif,--without-libgif,giflib"
PACKAGECONFIG[exif] = "--with-libexif,--without-libexif,libexif"
PACKAGECONFIG[pango] = "--with-pango,,pango"
PACKAGECONFIG[x11] = ",--without-x11,virtual/libx11 libxft"

DEPENDS =+ "cairo freetype fontconfig libpng"

do_install:append() {
# fix pkgconfig .pc file
sed -i -e s#I${STAGING_DIR_HOST}#I#g ${D}${libdir}/pkgconfig/*.pc
}
