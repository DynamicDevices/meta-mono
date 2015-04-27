require mono-4.xx.inc

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"

SRC_URI[md5sum] = "426065ce1aba25fd3e776ea736c5f430"
SRC_URI[sha256sum] = "ff1f15f3b8d43c6a2818c00fabe377b2d8408ad14acd9d507658b4cae00f5bce"
