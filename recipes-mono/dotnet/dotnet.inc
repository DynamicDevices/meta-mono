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
RDEPENDS:${PN}:remove:class-nativesdk = "libgssapi-krb5"

PR = "r0"

SRC_ARCH:aarch64 = "arm64"
SRC_ARCH:arm = "arm"
SRC_ARCH:x86-64 = "x64"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install[vardeps] += "DOTNET_RUNTIME_VERSION"

do_install() {
    install -d ${D}${bindir}
    ln -rs ${D}${datadir}/dotnet/dotnet ${D}${bindir}/dotnet

    install -d ${D}${datadir}/dotnet
    cp -r --no-preserve=ownership  ${S}/templates ${D}${datadir}/dotnet
    install -m 0755 ${S}/dotnet ${D}${datadir}/dotnet
    install -m 0644 ${S}/LICENSE.txt ${D}${datadir}/dotnet
    install -m 0644 ${S}/ThirdPartyNotices.txt ${D}${datadir}/dotnet

    install -d ${D}${datadir}/dotnet/host/fxr
    cp -r --no-preserve=ownership ${S}/host/fxr/${DOTNET_RUNTIME_VERSION} ${D}${datadir}/dotnet/host/fxr

    cp -r --no-preserve=ownership ${S}/sdk ${D}${datadir}/dotnet/
    cp -r --no-preserve=ownership ${S}/sdk-manifests ${D}${datadir}/dotnet/

    install -d ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME_VERSION} ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App

    install -d ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.AspNetCore.App/${DOTNET_RUNTIME_VERSION} ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App

    # Hack to fix liblttng-ust dependency issues
    patchelf --remove-needed liblttng-ust.so.0 ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME_VERSION}/libcoreclrtraceptprovider.so

    install -d ${D}${libdir}
    ln -rs ${D}${datadir}/dotnet/host/fxr/${DOTNET_RUNTIME_VERSION}/libhostfxr.so ${D}${libdir}/libhostfxr.so
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

BBCLASSEXTEND = "native nativesdk"
