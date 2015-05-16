require mono-4.xx.inc

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no "

# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

SRC_URI += "file://fix-basic-mscorlib-dep.patch \
	    file://0001-add-missing-visualbasic-targets.patch \
"

S = "${WORKDIR}/mono-4.0.1"

inherit native

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"

SRC_URI[md5sum] = "24762be2b396cb8d4c5b49dc863bc2fd"
SRC_URI[sha256sum] = "220125938cb85482a398e11ed90265eecf4df5defb259ade7e30208ecb859c04"

