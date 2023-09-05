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
SRC_FETCH_ID:aarch64 = "16b0b1af-6eab-4f9e-b9a4-9b29f6a1d681/4624e54b61cae05b1025211482f9c5e9"
SRC_SHA512SUM:aarch64 = "474879abcf40d4a06d54e02997a3fb93dd10c8d5f0dfd5acbf7e1a6f493a6d3421e426431d512b482c62cda92d7cda4eddd8bab80f923d0d2da583edaa8905e8"

SRC_ARCH:arm = "arm"
SRC_FETCH_ID:arm = "59aac694-6949-44c1-960b-2b6feca7c36c/d670fb92396fb5bdb0173b9114611841"
SRC_SHA512SUM:arm = "680641bb807483831fae57c07b635b710be10c1a2791fc98d371cf7417c2f78f187f7542d15177482237ada5a4c874b35318fd0a0f66f0e42bd531048092c1b9"

SRC_ARCH:x86-64 = "x64"
SRC_FETCH_ID:x86-64 = "dbfe6cc7-dd82-4cec-b267-31ed988b1652/c60ab4793c3714be878abcb9aa834b63"
SRC_SHA512SUM:x86-64 = "4cfeedb8e99ffd423da7a99159ee3f31535fd142711941b8206542acb6be26638fbd9a184a5d904084ffdbd8362c83b6b2acf9d193b2cd38bf7f061443439e3c"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

DOTNET_RUNTIME = "7.0.10"
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
INSANE_SKIP:${PN}-dbg = "libdir"

BBCLASSEXTEND = "native"
