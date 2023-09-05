SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"

BRANCH ?= "main"

BRANCH_googletest ?= "v1.8.x"
SRCREV_googletest = "dea0216d0c6bc5e63cf5f6c8651cd268668032ec"

SRC_URI = " \
	git://github.com/mono/libgdiplus.git;protocol=https;branch=${BRANCH} \
	gitsm://github.com/google/googletest.git;protocol=https;name=googletest;branch=${BRANCH_googletest};destsuffix=git/external/googletest \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP:${PN} += "dev-so"
