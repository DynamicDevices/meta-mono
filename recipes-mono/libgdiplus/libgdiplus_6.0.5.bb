require libgdiplus-common.inc
require libgdiplus-6.xx.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=e0a7dacaa67d7e24a32074fba736dc59"

SRC_URI += " \
		file://0001-fix-cross-compile.patch \
		"

SRC_URI[md5sum] = "8079300e708c7ea9b4254d4b2eeba463"
SRC_URI[sha256sum] = "1fd034f4b636214cc24e94c563cd10b3f3444d9f0660927b60e63fd4131d97fa"
