SUMMARY = "C# implementation of D-Bus"
DESCRIPTION = "An inter-process communication framework that lets applications	interface with the system event bus as well as allowing them to talk to one another in a peer-to-peer configuration."
SECTION = "devel/mono"
DEPENDS = "mono"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "files://COPYING;md5=09d60852216ea29fdba9ea146513336c"

inherit autotools

def dbus_sharp_download_version(d):
    pvsplit = d.getVar('PV', True).split('.')
    return pvsplit[0] + '.' + pvsplit[1]

SDIRVER = "${@dbus_sharp_download_version(d)}"

SRC_URI = "https://github.com/mono/dbus-sharp/archive/v${SDIRVER}.tar.gz"

S = "${WORKDIR}/${PN}-${SDIRVER}"

FILESPATH =. "${FILE_DIRNAME}/${PN}-${PV}:"

FILES_${PN} += " \
  ${libdir}/mono/dbus-sharp-2.0/* \
  ${libdir}/mono/gac/dbus-sharp/*/*.dll \
  ${libdir}/mono/gac/dbus-sharp/*/*.config \
"

FILES_${PN}-dbg += " \
  ${libdir}/mono/gac/dbus-sharp/*/*.mdb \
"

