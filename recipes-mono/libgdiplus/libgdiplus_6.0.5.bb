require libgdiplus-common.inc
require libgdiplus-6.xx.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=e0a7dacaa67d7e24a32074fba736dc59"

SRC_URI += " \
		file://0001-fix-cross-compile.patch \
		"

BRANCH = "release/6.0"
SRCREV = "110bdc284272258a0d9c95db0de8fcf34b6888b0"
