SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

inherit autotools-brokensep

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.tar.bz2 \
	   file://dllmap-config.in.diff \
	   file://Makefile.am.diff \
	  "

SRC_URI[md5sum] = "1075f99bd8a69890af9e30309728e684"
SRC_URI[sha256sum] = "fdb48cad26149288dcb99a3d9b4ec89735706000242c50bdc21ce657af201a40"

FILESPATH =. "${FILE_DIRNAME}/mono-${PV}:"

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
    sed -e "s|slash\}libtool|slash\}${HOST_SYS}-libtool|" -i acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i libgc/acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i eglib/acinclude.m4
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/mono-wrapper.in
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/monodis-wrapper.in
}

