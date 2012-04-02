SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.0.tar.bz2 \
           file://mono-2.11/Makefile.am.diff \
           file://mono-2.11/data/config.in.diff"

SRC_URI[md5sum] = "8f31f366da9f7e4b69ee23e8bf5b6b3d"
SRC_URI[sha256sum] = "de6515a4e1f73229711eaa5557009ec59a676554f8d48c45fc88944afd720f85"

CFLAGS_append += " -DARM_FPU_NONE=1 "

