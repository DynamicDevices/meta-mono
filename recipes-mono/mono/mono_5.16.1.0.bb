require mono-5.xx.inc
require mono-mit-bsd.inc
require ${PN}-base.inc
require mono-${PV}.inc

SRC_URI += "file://shm_open-works.patch"

PACKAGES += "${PN}-profiler "
FILES_${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

do_configure_append () {
	rm -rf ${S}/external/corefx/src/Native/Unix/System.Native/{.libs/*,*.{o,lo}}
}
