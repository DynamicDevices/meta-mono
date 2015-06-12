FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SUMMARY = "Mono Mozilla Root Certificates"

LICENSE = "MPL-1.1"
LIC_FILES_CHKSUM = "file://../certdata.txt;md5=b5b009a1c475f7bb95ac8c55f80442f3"

#
# Note that this local file is taken from the default mozroots source URI here
#
# http://mxr.mozilla.org/seamonkey/source/security/nss/lib/ckfw/builtins/certdata.txt?raw=1
#
# There are newer certdata.txt files available. These do not seem to import cleanly with
# older versions of Mono (e.g. 3.12.1) but do seem to import with Mono 4.0.2
#
# see: http://curl.haxx.se/mail/archive-2013-12/0033.html
#

SRC_URI = "file://certdata.txt"

SRC_URI[md5sum] = "b5b009a1c475f7bb95ac8c55f80442f3"
SRC_URI[sha256sum] = "ea89ac8ae495e69586abae22941816842ca5811a32a20dc9e1adb95859802879"

do_install_append() {
 install -d "${D}${sysconfdir}"
 install -d "${D}${sysconfdir}/ssl"
 install -m 644 ${S}/../certdata.txt ${D}${sysconfdir}/ssl/certdata.txt
}

FILES_${PN} = "${sysconfdir}/ssl/certdata.txt"

inherit allarch

# Do post installation install
pkg_postinst_${PN} () {
     #!/bin/sh -e
     if [ x"$D" = "x" ]; then
          # Actions to carry out on the device go here
          mono /usr/lib/mono/4.5/mozroots.exe --import --machine --ask-remove --file ${sysconfdir}/ssl/certdata.txt
     else
          exit 1
     fi
}

