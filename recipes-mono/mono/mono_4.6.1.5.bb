require mono-4.xx.inc
require mono-mit.inc
require ${PN}-base.inc
require mono-${PV}.inc

PACKAGES += "${PN}-profiler "
FILES_${PN}-profiler += " ${datadir}/mono-2.0/mono/profiler/*"

SRC_URI += "file://ARM-Disable-fast-tls-on-embedded-mono.patch \
"

