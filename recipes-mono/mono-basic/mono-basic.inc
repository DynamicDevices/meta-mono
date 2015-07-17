DESCRIPTION = "Visual Basic.NET support in Mono"
SECTION = "devel/mono"
DEPENDS = "mono"
RDEPENDS_${PN} = "mono"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d8c5dc22eb6f25667cf8d96f603635e7"

inherit autotools-brokensep

SRC_URI = "http://download.mono-project.com/sources/mono-basic/mono-basic-${PV}.tar.bz2"

FILES_${PN} += " \
  ${libdir}/mono/*/* \
  ${libdir}/mono/gac/*/*/*.dll \
"

FILES_${PN}-dbg += " \
  ${libdir}/mono/*/*.mdb \
  ${libdir}/mono/*/*.Pdb \
  ${libdir}/mono/*/*.Mdb \
  ${libdir}/mono/*/*/*/*.mdb \
"

do_compile_prepend() {
  sed -e "s|\$(prefix)|${STAGING_DIR_NATIVE}/usr|" -i build/profiles/net_2_0.make
  sed -e "s|\$(prefix)|${STAGING_DIR_NATIVE}/usr|" -i build/profiles/net_4_0.make
}

do_install_append() {
 install -d "${D}${libdir}/mono/4.5"
 ln -sf ${libdir}/mono/4.0/Microsoft.VisualBasic.dll  ${D}${libdir}/mono/4.5/Microsoft.VisualBasic.dll
}

