DESCRIPTION = ".NET Core Runtime, SDK & CLI tools"
HOMEPAGE = "https://www.microsoft.com/net/core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9fc642ff452b28d62ab19b7eea50dfb9"

COMPATIBLE_HOST ?= "(x86_64|aarch64|arm).*-linux"

DEPENDS = "patchelf-native"

#FIXME add lttng-ust as soon as dotnet core supports liblttng-ust.so.1
RDEPENDS:${PN} = "\
    icu \
    libgssapi-krb5 \
    zlib \
"

RDEPENDS:${PN}:remove:class-native = "libgssapi-krb5"

PR = "r0"

SRC_ARCH:aarch64 = "arm64"
SRC_FETCH_ID:aarch64 = "03972b46-ddcd-4529-b8e0-df5c1264cd98/285a1f545020e3ddc47d15cf95ca7a33"
SRC_SHA512SUM:aarch64 = "39cada75d9b92797de304987437498d853e1a525b38fa72d0d2949932a092fcf6036b055678686db42682b5b79cdc5ec5995cb01aa186762e081eb1ed38d2364"

SRC_ARCH:arm = "arm"
SRC_FETCH_ID:arm = "a72dea03-21fd-48c6-bf0c-78e621b60514/e0b8f186730fce858eb1bffc83c9e41c"
SRC_SHA512SUM:arm = "f14d5d24d6611139cff313275599923856307c3afe57d2439eb95e2a89da5fb54fdb6ce5b0d7a243f2d031af4eb0c277ce1914725f8b9dcff4002fcc05489f08"

SRC_ARCH:x86-64 = "x64"
SRC_FETCH_ID:x86-64 = "1cac4d08-3025-4c00-972d-5c7ea446d1d7/a83bc5cbedf8b90495802ccfedaeb2e6"
SRC_SHA512SUM:x86-64 = "997caff60dbad7259db7e3dd89886fc86b733fa6c1bd3864c8199f704eb24ee59395e327c43bb7c0ed74e57ec412bd616ea26f02f8f8668d04423d6f8e0a8a33"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

DOTNET_RUNTIME = "6.0.25"
do_install[vardeps] += "DOTNET_RUNTIME"

do_install() {
    install -d ${D}${bindir}
    ln -rs ${D}${datadir}/dotnet/dotnet ${D}${bindir}/dotnet

    install -d ${D}${datadir}/dotnet
    cp -r --no-preserve=ownership  ${S}/templates ${D}${datadir}/dotnet
    install -m 0755 ${S}/dotnet ${D}${datadir}/dotnet
    install -m 0644 ${S}/LICENSE.txt ${D}${datadir}/dotnet
    install -m 0644 ${S}/ThirdPartyNotices.txt ${D}${datadir}/dotnet

    install -d ${D}${datadir}/dotnet/host/fxr
    cp -r --no-preserve=ownership ${S}/host/fxr/${DOTNET_RUNTIME} ${D}${datadir}/dotnet/host/fxr

    cp -r --no-preserve=ownership ${S}/sdk ${D}${datadir}/dotnet/
    cp -r --no-preserve=ownership ${S}/sdk-manifests ${D}${datadir}/dotnet/

    install -d ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME} ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App

    install -d ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.AspNetCore.App/${DOTNET_RUNTIME} ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App

    # Hack to fix liblttng-ust dependency issues
    patchelf --remove-needed liblttng-ust.so.0 ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME}/libcoreclrtraceptprovider.so

    install -d ${D}${libdir}
    ln -rs ${D}${datadir}/dotnet/host/fxr/${DOTNET_RUNTIME}/libhostfxr.so ${D}${libdir}/libhostfxr.so
}

do_install:append:x86-64:class-target () {
    # Set correct interpreter path
    patchelf --set-interpreter ${base_libdir}/ld-linux-x86-64.so.2 ${D}${datadir}/dotnet/dotnet
}

FILES:${PN} += "\
    ${datadir}/dotnet/dotnet \
    ${datadir}/dotnet/*.txt \
    ${datadir}/dotnet/host \
    ${datadir}/dotnet/shared \
    ${libdir} \
"

FILES:${PN}-dev = "\
    ${datadir}/dotnet/sdk \
    ${datadir}/dotnet/sdk-manifests \
    ${datadir}/dotnet/templates \
"

FILES:${PN}-dbg = "\
    ${datadir}/dotnet/.debug \
"

RRECOMMENDS:dotnet-dev[nodeprrecs] = "1"

INSANE_SKIP:${PN} = "already-stripped libdir staticdev textrel dev-so"

BBCLASSEXTEND = "native"
