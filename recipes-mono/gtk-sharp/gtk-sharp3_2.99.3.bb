require gtk-sharp.inc

inherit pkgconfig

DEPENDS += " gtk+3 atk pango cairo glib-2.0 libglade mono"
RDEPENDS_${PN} += " perl gtk+3"

LIC_FILES_CHKSUM = "files://COPYING;md5=8754deb904d22254188cb67189b87f19"

SDIRVERN = "${@gtk_sharp_download_versionN(d)}"
SEXT ??= "bz2"

def gtk_sharp_download_versionN(d):
    pvsplit = d.getVar('PV', True).split('.')
    return pvsplit[0] + '.' + pvsplit[1]

SRC_URI = "https://download.gnome.org/sources/gtk-sharp/${SDIRVERN}/gtk-sharp-${PV}.tar.xz \
           file://0001-fixup-gmcs-to-mcs.patch"

SRC_URI[md5sum] = "2120ff15abe655e4de8aa5aadf0d5d12"
SRC_URI[sha256sum] = "6440f571416267ae0cb5698071d087b31e3084693fa2c829b1db37ca7ea2c3a2"

S = "${WORKDIR}/gtk-sharp-${PV}"

do_configure_prepend() {
  export PROFILER_CFLAGS="-D_REENTRANT -I${STAGING_DIR_TARGET}/usr/include/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include -I${STAGING_DIR_TARGET}/usr/include/mono-2.0"
}

do_install_append() {
  rm -f ${D}${libdir}/libmono-profiler-gui-thread-check.so 
}

FILES_${PN} += "\
  ${libdir}/gapi-3.0/* \
"
