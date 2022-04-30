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
SRC_FETCH_ID:aarch64 = "8be77687-0931-40e4-8e50-b1b4cb367277/eef06721da36a9395dbaf8cb879ba820"
SRC_SHA512SUM:aarch64 = "500428a6a99d3825400be5cc1a723283f21a98c15ef68914e9252fc872b625fd10c220afd9787b7db6b226a04e83d30658234d464ccdf838639920d1768025e0"

SRC_ARCH:arm = "arm"
SRC_FETCH_ID:arm = "17588888-bd97-41e6-a1ef-9f1da6b8bdf6/ab16a7b0b82297f76abc793b5d187952"
SRC_SHA512SUM:arm = "37db60df90f9f43a5399c1b7929c545e3815d7a91fdea82c96a136dcef993b0f8c67568759ae24321dee818fd1501cea8e541a159e10ed1a08fa96dddf2ffb9e"

SRC_ARCH:x86-64 = "x64"
SRC_FETCH_ID:x86-64 = "06628342-344d-4524-ba62-e4762c0322f2/49fc2867cf4cfde29c721ff3b03cdf1b"
SRC_SHA512SUM:x86-64 = "b45f1bf086bfb5e0701c5e14534524ffc87d0195358ac4fa2cf36dac74537ca4c21c7177cfbfa7e121e77aa4106bb1e7039c9739ad73b942e2437bc5e39e6dce"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

RUNTIME = "5.0.16"
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

    install -d ${D}${datadir}/dotnet/sdk
    cp -r --no-preserve=ownership ${S}/sdk/${PV} ${D}${datadir}/dotnet/sdk

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
    ${datadir}/dotnet/templates \
"

FILES:${PN}-dbg = "\
    ${datadir}/dotnet/.debug \
"

RRECOMMENDS:dotnet-dev[nodeprrecs] = "1"

INSANE_SKIP:${PN} = "already-stripped libdir staticdev textrel"

BBCLASSEXTEND = "native"
