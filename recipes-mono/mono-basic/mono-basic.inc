DESCRIPTION = "Visual Basic.NET support in Mono"
SECTION = "devel/mono"
DEPENDS = "mono"
RDEPENDS_${PN} = "mono"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d8c5dc22eb6f25667cf8d96f603635e7"

inherit autotools 

SRC_URI = "http://download.mono-project.com/sources/mono-basic/mono-basic-${PV}.tar.bz2"

FILES_${PN} += " \
  ${libdir}/mono/2.0/*.dll \
  ${libdir}/mono/4.0/*.dll \
  ${libdir}/mono/4.0/*.exe \
  ${libdir}/mono/4.0/*.rsp \
  ${libdir}/mono/4.5/* \
  ${libdir}/mono/gac/Mono.Cecil.VB \
  ${libdir}/mono/gac/Microsoft.VisualBasic \
  ${libdir}/mono/gac/Mono.Cecil.VB.Pdb/0.9.3.0__0738eb9f132ed756/*.dll \
  ${libdir}/mono/gac/Microsoft.VisualBasic/10.0.0.0__b03f5f7f11d50a3a/*.dll \
  ${libdir}/mono/gac/Microsoft.VisualBasic/8.0.0.0__b03f5f7f11d50a3a/*.dll \
  ${libdir}/mono/gac/Mono.Cecil.VB.Mdb/0.9.3.0__0738eb9f132ed756/*.dll \
  ${libdir}/mono/gac/Mono.Cecil.VB/0.9.3.0__0738eb9f132ed756/*.dll \
"

FILES_${PN}-dbg += " \
  ${libdir}/mono/4.0/*.mdb \
  ${libdir}/mono/gac/*.Pdb \
  ${libdir}/mono/gac/*.Mdb \
  ${libdir}/mono/gac/Mono.Cecil.VB.Pdb/0.9.3.0__0738eb9f132ed756/*.mdb \
  ${libdir}/mono/gac/Microsoft.VisualBasic/10.0.0.0__b03f5f7f11d50a3a/*.mdb \
  ${libdir}/mono/gac/Microsoft.VisualBasic/8.0.0.0__b03f5f7f11d50a3a/*.mdb \
  ${libdir}/mono/gac/Mono.Cecil.VB.Mdb/0.9.3.0__0738eb9f132ed756/*.mdb \
  ${libdir}/mono/gac/Mono.Cecil.VB/0.9.3.0__0738eb9f132ed756/*.mdb \
"

do_compile_prepend() {
  sed -e "s|\$(prefix)|${STAGING_DIR_NATIVE}/usr|" -i build/profiles/net_2_0.make
  sed -e "s|\$(prefix)|${STAGING_DIR_NATIVE}/usr|" -i build/profiles/net_4_0.make
}

do_install_append() {
 install -d "${D}${libdir}/mono/4.5"
 ln -sf ${libdir}/mono/4.0/Microsoft.VisualBasic.dll  ${D}${libdir}/mono/4.5/Microsoft.VisualBasic.dll
}

