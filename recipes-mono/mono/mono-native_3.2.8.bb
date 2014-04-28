require mono-${PV}.inc

inherit native

DEPENDS =+ "libgdiplus-native"

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}
