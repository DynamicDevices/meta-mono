SUMMARY = "Mono.Addins"
DESCRIPTION = "Mono.Addins is a generic framework for creating extensible applications, and for creating add-ins which extend those applications."
AUTHOR = "Lluis Sanchez Gual  <slluis.dev@gmail.com>"
HOMEPAGE = "http://www.mono-project.com/Mono.Addins"
SECTION = "mono/applications"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=4e024d772d8266e3ff9747185842ca82"
DEPENDS = "mono"

SRCREV = "64a45d96f39d4714ec85adf0fe04b68ec7273ae1"
SRCBRANCH = "master"

SRC_URI = "git://github.com/mono/mono-addins.git;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

do_configure() {
}

do_compile() {
        xbuild 
}

do_install() {
}

do_install() {

	gacutil /i -gacdir ${D}/usr ${S}/bin/Mono.Addins.CecilReflector.dll 
	gacutil /i -gacdir ${D}/usr ${S}/bin/Mono.Addins.Gui.dll 
	gacutil /i -gacdir ${D}/usr ${S}/bin/Mono.Addins.MSBuild.dll 
	gacutil /i -gacdir ${D}/usr ${S}/bin/Mono.Addins.Setup.dll 
	gacutil /i -gacdir ${D}/usr ${S}/bin/Mono.Addins.dll

	install -d ${D}/${bindir}
        install -m 0755 ${S}/bin/mautil.exe ${D}${bindir}/mautil.exe
}

FILES_${PN} += " \
  ${libdir}/mono/gac/*/*/*.dll \
  ${libdir}/mono/gac/*/*/*.config \
"

FILES_${PN}-dbg += " \
  ${libdir}/mono/gac/*/*/*.mdb \
"
