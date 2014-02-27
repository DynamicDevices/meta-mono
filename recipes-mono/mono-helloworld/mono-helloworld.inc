SUMMARY = "Mono Hello World"
DESCRIPTION = "Test applications for Mono console and windows forms"
AUTHOR = "Alex J Lennon <ajlennon@dynamicdevices.co.uk>"
HOMEPAGE = "http://www.dynamicdevices.co.uk"
SECTION = "mono/applications"
PRIORITY = "optional"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=783b7e40cdfb4a1344d15b1f7081af66"
DEPENDS = "mono"
PR = "r0"

SRC_URI = "https://github.com/DynamicDevices/mono-helloworld/archive/v${PV}.tar.gz"

inherit autotools

FILES_${PN} = "${libdir}/helloworld/helloworld.exe \
		${bindir}/helloworld \
        	${libdir}/helloworld/helloworldform.exe \
		${bindir}/helloworldform \
"
