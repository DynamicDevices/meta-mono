SUMMARY = "An Open Source implementation of the Microsoft's .NET Framework"
DESCRIPTION = "This is part of the Mono project - http://mono-project.com"
HOMEPAGE = "http://mono-project.com"
BUGTRACKER = "http://bugzilla.xamarin.com/"
SECTION = "devel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=80862f3fd0e11a5fa0318070c54461ce"

inherit autotools-brokensep

SRCBRANCH = "master"
SRCREV = "${AUTOREV}"

FORK = "mono"

SRC_URI = "git://github.com/${FORK}/mono.git;branch=${SRCBRANCH}\
           file://dllmap-config.in.diff \
"

# Custom GitHub fork / custom branch example
#SRCBRANCH = "ajl/mail"
#FORK = "DynamicDevices"

S = "${WORKDIR}/git"

FILESPATH =. "${FILE_DIRNAME}/mono-git:"

do_configure_prepend() {
    ${S}/autogen.sh --verbose || bbnote "${PN} failed to autogen.sh"
    sed -e "s|slash\}libtool|slash\}${HOST_SYS}-libtool|" -i acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i libgc/acinclude.m4
    sed -e "s|slash\}libtool|slash\}../${HOST_SYS}-libtool|" -i eglib/acinclude.m4
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/mono-wrapper.in
    sed -e "s|r/libtool|r/${HOST_SYS}-libtool|" -i runtime/monodis-wrapper.in
}

