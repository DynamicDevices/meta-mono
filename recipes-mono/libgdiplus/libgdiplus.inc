PACKAGECONFIG ??= "jpeg tiff gif exif"
PACKAGECONFIG[jpeg] = "--with-libjpeg,--without-libjpeg,jpeg"
PACKAGECONFIG[tiff] = "--with-libtiff,--without-libtiff,tiff"
PACKAGECONFIG[gif] = "--with-libgif,--without-libgif,giflib"
PACKAGECONFIG[exif] = "--with-libexif,--without-libexif,libexif"

DEPENDS =+ "cairo freetype fontconfig libxft libpng"

do_install_append() {
# fix pkgconfig .pc file
sed -i -e s#I${STAGING_DIR_HOST}#I#g ${D}${libdir}/pkgconfig/*.pc
}
