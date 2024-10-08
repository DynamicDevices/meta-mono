SUMMARY = "taglib-sharp"
DESCRIPTION = "Library for reading and writing metadata in media files"
AUTHOR = "Gabriel Burt <gabriel.burt@gmail.com>"
HOMEPAGE = "https://github.com/mono/taglib-sharp"
SECTION = "mono/applications"
PRIORITY = "optional"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"
DEPENDS = "mono"

SRC_URI = "git://github.com/mono/taglib-sharp.git;protocol=https;branch=${SRCBRANCH}"

S = "${UNPACKDIR}/git"

inherit autotools-brokensep pkgconfig

EXTRA_OECONF = " --disable-docs"

do_configure:prepend () {
  sed -e "s|\$(datadir)/pkgconfig|\$(libdir)/pkgconfig|" -i Makefile.am
}

FILES:${PN} += " \
  ${libdir}/mono/${PN}/*.dll \
  ${libdir}/mono/gac/*\ 
  ${libdir}/mono/gac/*/*/*/*.dll \
  ${libdir}/mono/gac/*/*/*/*..config \
"

FILES:${PN}-dbg += " \
  ${libdir}/mono/${PN}/*.mdb \
  ${libdir}/mono/gac/*/*/*/*.mdb \
"
