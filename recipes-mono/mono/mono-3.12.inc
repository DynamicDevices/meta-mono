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
          "

SRC_URI[sha256sum] = "8fdc69329c8d669d1c5e5bc922a5df8a3ed61a72344b92e5a073809bf1b36783"

FILESPATH =. "${FILE_DIRNAME}/mono-${PV}:"

do_configure:prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
}
