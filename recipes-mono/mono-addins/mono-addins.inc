SUMMARY = "Mono.Addins"
DESCRIPTION = "Mono.Addins is a generic framework for creating extensible applications, and for creating add-ins which extend those applications."
AUTHOR = "Lluis Sanchez Gual  <slluis.dev@gmail.com>"
HOMEPAGE = "http://www.mono-project.com/Mono.Addins"
SECTION = "mono/applications"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=4e024d772d8266e3ff9747185842ca82"
DEPENDS = "gtk-sharp"

SRCREV = "64a45d96f39d4714ec85adf0fe04b68ec7273ae1"
SRCBRANCH = "master"

SRC_URI = "git://github.com/mono/mono-addins.git;branch=${SRCBRANCH} \
	   file://0001-configure-mcs.patch"

S = "${WORKDIR}/git"

inherit autotools-brokensep pkgconfig
inherit mono
