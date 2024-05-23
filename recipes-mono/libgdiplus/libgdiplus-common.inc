SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"

BRANCH ?= "main"

SRC_URI = " \
	gitsm://github.com/mono/libgdiplus.git;protocol=https;branch=${BRANCH} \
"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP:${PN} += "dev-so"
