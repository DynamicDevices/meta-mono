# Default configuration, distros might want to override
PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"

inherit native

RPROVIDES:${PN} += "nativesdk-mono"
PROVIDES += "nativesdk-mono"

do_compile() {
    oe_runmake EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

EDEPENDS_X11 =+ "libgdiplus-native"
PACKAGECONFIG[x11] = ",,${EDEPENDS_X11}"

mono_copy_libdir_mono() {
	install -d -m0755 ${STAGING_LIBDIR_NATIVE}
	cp -arf ${SYSROOT_DESTDIR}${STAGING_LIBDIR_NATIVE}/mono ${STAGING_LIBDIR_NATIVE}/
}

do_populate_sysroot[postfuncs] += " mono_copy_libdir_mono "

do_install:append() {
    sed "s|\$mono_libdir|${libdir}|g" -i ${D}${sysconfdir}/mono/config
}
