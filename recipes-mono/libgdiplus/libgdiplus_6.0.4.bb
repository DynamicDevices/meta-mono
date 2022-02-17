require libgdiplus-common.inc
require libgdiplus-6.xx.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=e0a7dacaa67d7e24a32074fba736dc59"

SRC_URI += " \
		file://0001-fix-cross-compile.patch \
		"

SRC_URI[md5sum] = "3f94b3d61934eecccaaac5a49501b283"
SRC_URI[sha256sum] = "9a5e3f98018116f99361520348e9713cd05680c231d689a83d87acfaf237d3a8"
