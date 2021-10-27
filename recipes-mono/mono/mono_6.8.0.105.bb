require mono-6.xx.inc
require mono-mit-bsd-6xx.inc
require ${PN}-base.inc
require mono-${PV}.inc

SRC_URI += "file://shm_open-test-crosscompile.diff"

PACKAGES += "${PN}-profiler "
FILES_${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

INSANE_SKIP_${PN}-libs += "dev-so"
