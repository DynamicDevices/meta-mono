SUMMARY = "Mono.Upnp"
DESCRIPTION = "Mono.Upnp is set of client/server libraries for the Universal Plug 'n Play specifications."
AUTHOR = "Alexander Kojevnikov <alexander@kojevnikov.com>"
HOMEPAGE = "https://github.com/mono/mono-upnp"
SECTION = "mono/applications"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=2b0fcb9ea37f23f59acc7163e7727d39"
DEPENDS = "mono mono-addins gtk-sharp taglib-sharp"

SRCREV = "b374ed41c566bc6ad50b3513218458b711e508db"
SRCBRANCH = "master"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI = "git://github.com/mono/mono-upnp.git;branch=${SRCBRANCH} \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig

do_configure () {
  export TAGLIB_SHARP_LIBS="-r:${STAGING_DIR_TARGET}/usr/lib/pkgconfig/../../lib/mono/taglib-sharp/taglib-sharp.dll"
  ./autogen.sh 
}

FILES_${PN}-dbg += " \
  ${libdir}/${PN}/*.mdb \
"


