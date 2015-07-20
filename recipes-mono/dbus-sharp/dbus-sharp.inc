SUMMARY = "C# implementation of D-Bus"
DESCRIPTION = "An inter-process communication framework that lets applications	interface with the system event bus as well as allowing them to talk to one another in a peer-to-peer configuration."
SECTION = "devel/mono"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "files://COPYING;md5=09d60852216ea29fdba9ea146513336c"

inherit autotools-brokensep
inherit pkgconfig
inherit mono

SRC_URI = "https://github.com/mono/dbus-sharp/archive/v${PV}.tar.gz"
