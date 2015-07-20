SUMMARY = "C# implementation of D-Bus GlibC integration module"
DESCRIPTION = "This is a fork of ndesk-dbus-glib, which provides GLib main loop integration for Managed D-Bus."
SECTION = "devel/mono"
DEPENDS = "dbus-sharp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "files://COPYING;md5=ea73672fbe49f67f1c1b44a7e763dab4"

inherit autotools
inherit pkgconfig
inherit mono

def dbus_sharp_glib_download_version(d):
    pvsplit = d.getVar('PV', True).split('.')
    return pvsplit[0] + '.' + pvsplit[1]

SDIRVER = "${@dbus_sharp_glib_download_version(d)}"

SRC_URI = "https://github.com/mono/dbus-sharp-glib/archive/v${SDIRVER}.tar.gz"

S = "${WORKDIR}/${PN}-${SDIRVER}"

FILESPATH =. "${FILE_DIRNAME}/${PN}-${PV}:"

do_configure_prepend() {
  export DBUS_SHARP_LIBS="/r:${STAGING_LIBDIR}/mono/dbus-sharp-2.0/dbus-sharp.dll"
}

