require mono-4.xx.inc

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

S = "${WORKDIR}/mono-4.0.2"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"

SRC_URI[md5sum] = "24b6dbc02f8b69f7a30d01c962dc0c4d"
SRC_URI[sha256sum] = "29d8d9998cadf97b7e6efe8ced4e8a3457b99f5384de58be6d8592df4146e931"

