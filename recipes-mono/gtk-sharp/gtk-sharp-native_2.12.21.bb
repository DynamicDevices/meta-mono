require gtk-sharp.inc

inherit pkgconfig native

DEPENDS += " gtk+-native atk-native pango-native cairo-native glib-2.0-native libglade-native "

LIC_FILES_CHKSUM = "files://COPYING;md5=f14599a2f089f6ff8c97e2baa4e3d575"

SRC_URI[md5sum] = "2e892f265877fe5c16f41b771edb7618"
SRC_URI[sha256sum] = "ea02da7000433115dcc008102e0c217479c06d74b2c3af5b76527784f933b088"

SEXT = "gz"

