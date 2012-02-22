SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.tar.gz \
           file://mono-2.10.8.1/Makefile.am.diff \
           file://mono-2.10.8.1/data/config.in.diff \
           file://mono-2.10.8.1/mcs/class/Makefile.diff"

SRC_URI[md5sum] = "c7fd34d59f67e2a13e5421ba2441bc9d"
SRC_URI[sha256sum] = "1dfc666f8b6e90079d9b290d7f9cf63c15bdbce23f0223cad3163c9c926c9040"

CFLAGS_append += " -DARM_FPU_NONE=1 "

