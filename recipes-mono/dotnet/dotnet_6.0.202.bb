DESCRIPTION = ".NET Core Runtime, SDK & CLI tools"
HOMEPAGE = "https://www.microsoft.com/net/core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9fc642ff452b28d62ab19b7eea50dfb9"

COMPATIBLE_HOST ?= "(x86_64|aarch64|arm).*-linux"

DEPENDS = "zlib"

#FIXME add lttng-ust as soon as dotnet core supports liblttng-ust.so.1
RDEPENDS:${PN} = "\
    icu \
    libgssapi-krb5 \
    zlib \
"

RDEPENDS:${PN}:remove:class-native = "libgssapi-krb5"

PR = "r0"

SRC_ARCH:aarch64 = "arm64"
SRC_FETCH_ID:aarch64 = "952f5525-7227-496f-85e5-09cadfb44629/eefd0f6eb8f809bfaf4f0661809ed826"
SRC_SHA512SUM:aarch64 = "2d0021bb4cd221ffba6888dbd6300e459f45f4f9d3cf7323f3b97ee0f093ef678f5a36d1c982296f4e15bbcbd7275ced72c3e9b2fc754039ba663d0612ffd866"

SRC_ARCH:arm = "arm"
SRC_FETCH_ID:arm = "e41a177d-9f0b-4afe-97a4-53587cd89d00/c2c897aa6442d49c1d2d86abb23c20b2"
SRC_SHA512SUM:arm = "8c2d56256f4bebe58caee7810b7689408ff023b1f2e68f99fa375f0115db41ef0c3eb160b9ab84dc2764443a045801a4b03f6bc9090e0c1583fca2587ea0d9d6"

SRC_ARCH:x86-64 = "x64"
SRC_FETCH_ID:x86-64 = "9d8c7137-2091-4fc6-a419-60ba59c8b9de/db0c5cda94f31d2260d369123de32d59"
SRC_SHA512SUM:x86-64 = "81e9c368d445d9e92e3af471d52dc2aa05e3ecb75ce95c13a2ed1d117852dae43d23d913bbe92eab730aef7f38a14488a1ac65c3b79444026a629647322c5798"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

RUNTIME = "6.0.4"
do_install[vardeps] += "RUNTIME"

do_install() {
    install -d ${D}${bindir}
    ln -rs ${D}${datadir}/dotnet/dotnet ${D}${bindir}/dotnet

    install -d ${D}${datadir}/dotnet
    cp -r --no-preserve=ownership  ${S}/templates ${D}${datadir}/dotnet
    install -m 0755 ${S}/dotnet ${D}${datadir}/dotnet
    install -m 0644 ${S}/LICENSE.txt ${D}${datadir}/dotnet
    install -m 0644 ${S}/ThirdPartyNotices.txt ${D}${datadir}/dotnet

    install -d ${D}${datadir}/dotnet/host/fxr
    cp -r --no-preserve=ownership ${S}/host/fxr/${RUNTIME} ${D}${datadir}/dotnet/host/fxr

    cp -r --no-preserve=ownership ${S}/sdk ${D}${datadir}/dotnet/
    cp -r --no-preserve=ownership ${S}/sdk-manifests ${D}${datadir}/dotnet/

    install -d ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.NETCore.App/${RUNTIME} ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App

    #FIXME: remove the following line. if the lttng-ust conflict is solved
    rm ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App/${RUNTIME}/libcoreclrtraceptprovider.so

    install -d ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.AspNetCore.App/${RUNTIME} ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App

    if [ "${SRC_ARCH}" = "x64" ]; then
        ln -s ${base_libdir} ${D}/lib64
    fi

}

FILES:${PN} += "\
    /lib64 \
    ${datadir}/dotnet/dotnet \
    ${datadir}/dotnet/*.txt \
    ${datadir}/dotnet/host \
    ${datadir}/dotnet/shared \
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

INSANE_SKIP:${PN} = "already-stripped libdir staticdev textrel"

BBCLASSEXTEND = "native"
