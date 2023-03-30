# Based on the recipe from from meta-iot-cloud
# Copyright © 2016 Intel Corporation

DESCRIPTION = ".NET Core Runtime, SDK & CLI tools"
HOMEPAGE = "https://www.microsoft.com/net/core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9fc642ff452b28d62ab19b7eea50dfb9"

COMPATIBLE_HOST ?= "(i586|x86_64).*-linux"

DEPENDS += "\
    zlib \
"

RDEPENDS:${PN}:class-target += "\
    lttng-ust \
    libcurl \
    krb5 \
    libgssapi-krb5 \
    libicuuc \
    libicui18n \
"

RDEPENDS:${PN}:class-native += "\
    curl-native \
    krb5-native \
    icu-native \
    zlib-native \
"

HOST_FXR = "3.1.11"
SHARED_FRAMEWORK = "3.1.11"
SDK = "3.1.111"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/d5d940c0-4c2f-4cbb-8d12-33bfb30c4db8/619b5be6e995cead6e9432134f903711/${BPN}-sdk-${SDK}-linux-x64.tar.gz"

SRC_URI[sha256sum] = "a755b37aa328d160a39b39c0cd120ca7e318814ef024682bac49e75183952a89"

S = "${WORKDIR}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

python do_install () {
    bb.build.exec_func("shell_do_install", d)
    oe.path.make_relative_symlink(d.expand("${D}${bindir}/dotnet"))
    oe.path.make_relative_symlink(d.expand("${D}${libdir}/libhostfxr.so"))
}

shell_do_install() {
    install -d ${D}${bindir}
    install -d ${D}${datadir}/dotnet
    install -d ${D}${datadir}/dotnet/host/fxr
    install -d ${D}${datadir}/dotnet/sdk
    install -d ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App

    install -m 0755 ${S}/dotnet ${D}${datadir}/dotnet
    install -m 0644 ${S}/LICENSE.txt ${D}${datadir}/dotnet
    install -m 0644 ${S}/ThirdPartyNotices.txt ${D}${datadir}/dotnet

    cp -r ${S}/sdk/${SDK} ${D}${datadir}/dotnet/sdk
    cp -r ${S}/host/fxr/${HOST_FXR} ${D}${datadir}/dotnet/host/fxr
    cp -r ${S}/shared/Microsoft.NETCore.App/${SHARED_FRAMEWORK} ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App
    cp -r ${S}/templates ${D}${datadir}/dotnet

    install -d ${D}${libdir}

    # Symlinks
    ln -s ${D}${datadir}/dotnet/dotnet ${D}${bindir}/dotnet
    ln -s ${D}${datadir}/dotnet/host/fxr/${HOST_FXR}/libhostfxr.so ${D}${libdir}/libhostfxr.so
}

FILES:${PN} = "\
    ${bindir} \
    ${libdir} \
    ${datadir}/dotnet/dotnet \
    ${datadir}/dotnet/*.txt \
    ${datadir}/dotnet/host \
    ${datadir}/dotnet/shared \
"

FILES:${PN}-dbg = "\
    ${datadir}/dotnet/.debug \
"

FILES:${PN}-dev = "\
    ${datadir}/dotnet/sdk \
    ${datadir}/dotnet/templates \
"

RRECOMMENDS:dotnet-dev[nodeprrecs] = "1"

INSANE_SKIP:${PN} = "already-stripped staticdev ldflags libdir"
INSANE_SKIP:${PN}-dbg = "libdir"
INSANE_SKIP:${PN}-dev = "libdir"

BBCLASSEXTEND = "native"
