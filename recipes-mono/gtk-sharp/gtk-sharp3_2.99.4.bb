require gtk-sharp.inc

inherit pkgconfig

DEPENDS += " gtk+3 atk pango cairo glib-2.0 glade mono"
RDEPENDS:${PN} += " perl gtk+3"

LIC_FILES_CHKSUM = "file://COPYING;md5=8754deb904d22254188cb67189b87f19"

SRCREV = "dadc19cf1b90c5743f2776c675faac990e397a56"
SRC_URI = "git://github.com/mono/gtk-sharp.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

do_configure:prepend() {
  export PROFILER_CFLAGS="-D_REENTRANT -I${STAGING_DIR_TARGET}/usr/include/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0 -I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include -I${STAGING_DIR_TARGET}/usr/include/mono-2.0"
}

do_install:append() {
  rm -f ${D}${libdir}/libmono-profiler-gui-thread-check.so 
}

FILES:${PN} += "\
  ${libdir}/gapi-3.0/* \
"
