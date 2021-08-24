require mono-5.xx.inc
require mono-mit-bsd.inc
require ${PN}-base.inc
require mono-${PV}.inc
require mono-5.16.inc

SRC_URI += "file://shm_open-test-crosscompile.diff"

PACKAGES += "${PN}-profiler "
FILES:${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"
