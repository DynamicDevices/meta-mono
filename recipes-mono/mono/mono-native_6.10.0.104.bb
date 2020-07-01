require mono-6.xx.inc
require mono-mit-bsd-6xx.inc
require mono-native-6.xx-base.inc
require mono-${PV}.inc

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.tar.xz \
           file://0001-patch-XplatUIX11-cursor.diff \
           file://shm_open-test-crosscompile.diff \
"

addtask fixup_config after do_patch before do_configure

do_fixup_config() {
        sed 's|$mono_libdir/libMonoPosixHelper@libsuffix@|libMonoPosixHelper.so|g' -i ${S}/data/config.in
        sed 's|@X11@|libX11.so.6|g' -i ${S}/data/config.in
        sed 's|@libgdiplus_install_loc@|libgdiplus.so.0|g' -i ${S}/data/config.in
}



