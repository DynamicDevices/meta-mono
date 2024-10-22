require mono-6.xx.inc
require mono-mit-bsd-6xx.inc
require ${PN}-base.inc
require mono-${PV}.inc

RDEPENDS:${PN}-dev =+ " zlib "

SRCREV = "0cbf0e290c31adb476f9de0fa44b1d8829affa40"

SRC_URI = "gitsm://github.com/mono/mono.git;protocol=https;branch=2020-02 \
           file://shm_open-test-crosscompile.diff \
           file://disable-mmap-MAP_32BIT-support.patch \
           file://0001-Allow-passing-external-mapfile-C-build-options.patch \
           file://0001-Add-libusb-1.0-mapping.patch \
"


addtask fixup_config after do_patch before do_configure

do_fixup_config() {
        sed 's|$mono_libdir/libMonoPosixHelper@libsuffix@|libMonoPosixHelper.so|g' -i ${S}/data/config.in
        sed 's|@X11@|libX11.so.6|g' -i ${S}/data/config.in
        sed 's|@libgdiplus_install_loc@|libgdiplus.so.0|g' -i ${S}/data/config.in
}

PACKAGES += "${PN}-profiler "
FILES:${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

INSANE_SKIP:${PN}-libs += "dev-so"
