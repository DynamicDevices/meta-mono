SUMMARY = "An Open Source implementation of the GDI+ API"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "libs"
LICENSE = "MIT"

SRC_URI = "https://github.com/mono/libgdiplus/archive/${PV}.tar.gz;downloadfilename=${BPN}-${PV}.tar.gz"

inherit autotools pkgconfig

FILES:${PN} += "${libdir}/libgdiplus.so"
INSANE_SKIP:${PN} += "dev-so"
