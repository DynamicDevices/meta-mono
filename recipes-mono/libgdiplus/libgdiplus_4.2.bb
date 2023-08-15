require libgdiplus-common.inc
require libgdiplus.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=fe7364dfce9f3689eb6995e7cdd56879"

SRC_URI += " \
		file://01-remove-libjpeg-path.patch \
		file://libgdiplus-2.10.9-format.patch \
	"

SRCREV = "109aeea93fbf8c9da94e9e9a8ed6c6e433c8d554"
