require mono-${PV}.inc

inherit native

DEPENDS =+ "libgdiplus-native"

EXTRA_OECONF += "mono_cv_uscore=no --with-sigaltstack=no --with-mcs-docs=no"

SRC_URI += "file://fix-basic-mscorlib-dep.patch"

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "mono-native failed to autogen.sh"
    sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i acinclude.m4
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/mono-wrapper.in
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/monodis-wrapper.in
}

do_compile() {
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}
