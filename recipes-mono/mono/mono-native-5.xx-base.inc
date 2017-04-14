# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"
