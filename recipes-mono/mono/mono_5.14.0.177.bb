require mono-5.xx.inc
require mono-mit-bsd.inc
require ${PN}-base.inc
require mono-${PV}.inc

PACKAGES += "${PN}-profiler "
FILES_${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"
