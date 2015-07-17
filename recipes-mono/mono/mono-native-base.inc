# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"
