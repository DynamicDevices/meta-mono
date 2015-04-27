FILESPATH =. "${FILE_DIRNAME}/${PN}:"

SUMMARY = "Xilium.CefGlue"
DESCRIPTION = "Xilium.CefGlue is a .NET/Mono binding for The Chromium Embedded Framework (CEF3)"
HOMEPAGE = "https://bitbucket.org/xilium/xilium.cefglue"
LICENSE = "MIT & BSD"

# Not ideal but there isn't a separate license file available
LIC_FILES_CHKSUM = "file://CefGlue.Interop.Gen/include/cef_v8.h;beginline=1;endline=35;md5=743cc905e6d381c52a72d50c4c8ea7a6"

SRC_URI = "https://bitbucket.org/xilium/xilium.cefglue/get/v${PV}.tar.bz2 \
	   file://build_4.0_client_profile.sh \
	   file://0001_disable_wpf_gtk_projects.patch \
          "

inherit autotools-brokensep

do_configure() {
  cp -f ${WORKDIR}/build_4.0_client_profile.sh ${S}
}

do_compile() {
  ./build_4.0_client_profile.sh
}

do_install() {
}

