require mono-4.xx.inc

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

S = "${WORKDIR}/mono-4.0.1"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"

SRC_URI[md5sum] = "2e71b347e23408548349492f75bfd2ca"
SRC_URI[sha256sum] = "31f7da2b2c3b39e4b3c549c0fef29604b22e827ac46cf6aa454bd5b22e4f2ec5"
