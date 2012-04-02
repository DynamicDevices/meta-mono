require mono-2.11.inc

inherit native autotools

PR = "r1"

EXTRA_OECONF += "mono_cv_uscore=no --with-tls=pthread --with-sigaltstack=no --with-mcs-docs=no"

do_configure_prepend() {
    autoreconf -Wcross --verbose --install --force || bbnote "mono-native failed to autoreconf"
    sed -e "s/libtool/${BUILD_SYS}-libtool/" -i runtime/monodis-wrapper.in
    sed -e "s/libtool/${BUILD_SYS}-libtool/" -i runtime/mono-wrapper.in
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i acinclude.m4
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i libgc/acinclude.m4
	sed -e "s/slash\}libtool/slash\}${HOST_SYS}-libtool/" -i eglib/acinclude.m4
}

