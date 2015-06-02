SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

inherit autotools

SRC_URI = "http://download.mono-project.com/sources/mono/mono-${PV}.tar.bz2 \
	   file://dllmap-config.in.diff \
           file://remove-the-client-side-SSLv2-fallback.patch \
           file://TLS-protocol-add-handshake-state-validation.patch \
           file://remove-the-export-ciphers-andrelated-code-path.patch \
	  "


SRC_URI[md5sum] = "f143bc5077fb817c0bedbe2d5bd49035"
SRC_URI[sha256sum] = "71b80d555f334a05a8db386e1c41e1a2f61a845c57726205f846e563a5ee2a4b"

FILESPATH =. "${FILE_DIRNAME}/mono-${PV}:"

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
    sed -e "s|slash\}libtool|slash\}${HOST_SYS}-libtool|" -i acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i libgc/acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i eglib/acinclude.m4
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/mono-wrapper.in
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/monodis-wrapper.in
}
