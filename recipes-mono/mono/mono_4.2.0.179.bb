require mono-4.xx.inc
require mono-gplv2.inc
require ${PN}-base.inc
require mono-${PV}.inc

SRC_URI += "file://fix-4.2.0-x86-build.patch"

PACKAGES += "${PN}-profiler "
FILES:${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

