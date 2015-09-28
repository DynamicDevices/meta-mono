require gtk-sharp.inc

inherit pkgconfig

DEPENDS += " gtk+3 atk pango cairo glib-2.0 libglade mono"
RDEPENDS_${PN} += " perl gtk+3"

LIC_FILES_CHKSUM = "files://COPYING;md5=8754deb904d22254188cb67189b87f19"

SRCREV = "9a72bb67fff7e4845b7bb430a608282668c3e4da"
SRC_URI = "git://github.com/mono/gtk-sharp.git;branch=master \
           file://0001-fixup-gmcs-to-mcs.patch"

S = "${WORKDIR}/git"

do_configure_prepend() {
  export PROFILER_CFLAGS="-D_REENTRANT -I${STAGING_DIR_TARGET}/usr/include/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include -I${STAGING_DIR_TARGET}/usr/include/mono-2.0"
}

do_install_append() {
  rm -f ${D}${libdir}/libmono-profiler-gui-thread-check.so 
}

FILES_${PN} += "\
  ${libdir}/gapi-3.0/* \
"
