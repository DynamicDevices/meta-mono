DESCRIPTION = "Monotools server (remote mono debugging)" 
SECTION = "mono" 
LICENSE = "MIT" 
# todo: Investigate license
LIC_FILES_CHKSUM = "file://install-sh;md5=6e5fe73723cd40a28adc5b7b5650c8d1" 
PR = "r0" 

# Download from Mono site
SRC_URI = "http://download.mono-project.com/monotools/latest/monotools-server-2.0.tar.bz2" 
SRC_URI[md5sum] = "44177a0933d0116a2ebb732395399689"
SRC_URI[sha256sum] = "dd0bc56e25f96e8c8525f327154940729fba2585de5d74b68a69d2ea30aee34b"

# Preferred download from Hg
SRC_REV = "47d89ae"
SRC_URI = "hg://bitbucket.org/jdluzen;rev=${SRC_REV};module=monotools-server;protocol=https" 

FILESEXTRAPATHS_prepend := "${THISDIR}/monotools-server-${PV}:"

SRC_URI += " \
	file://remove-about-program-name.patch \
	file://fix-build-dir.patch \
	file://fix-pkglib_SCRIPTS.patch \
 	file://fix-linq-reference.patch \
	file://0001-monotools-webserver-use-backlog.patch \
	file://0002-configure-use-mcs.patch \
	"

DEPENDS = "mono-xsp gtk-sharp"

S = "${WORKDIR}/${PN}"

inherit autotools-brokensep gettext pkgconfig

do_configure_prepend() {
    # Doesn't seem to be picked up by pkgconfig so override it
    export XSP_2_LIBS="${STAGING_LIBDIR}/mono/4.5/Mono.WebServer2.dll ${STAGING_LIBDIR}/mono/4.5/xsp4.exe"
}
