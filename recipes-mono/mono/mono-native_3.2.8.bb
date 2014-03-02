require mono-${PV}.inc

inherit native

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "mono-native failed to autogen.sh"
}

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" 
}
