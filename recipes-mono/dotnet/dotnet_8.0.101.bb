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
SRC_FETCH_ID:aarch64 = "092bec24-9cad-421d-9b43-458b3a7549aa/84280dbd1eef750f9ed1625339235c22"
SRC_SHA512SUM:aarch64 = "56beedb8181b63efd319b028190a8a98842efd96da27c5e48e18c4d15ba1a5805610e8838f1904a19263abd51ff68df369973ed59dab879edc52f6e7f93517c6"

SRC_ARCH:arm = "arm"
SRC_FETCH_ID:arm = "28bc9d47-631c-4e28-9e8c-3e8d025cc999/c4302b73c98da0dc26bcb36ed1e148d2"
SRC_SHA512SUM:arm = "59e0902fa190dee8da1644135e0477ced70fa02ecc12f79c8947743a77a160861ed5e44f8a4228815f853141856d4e3a1db1bd057759d3bff980a79b7d849689"

SRC_ARCH:x86-64 = "x64"
SRC_FETCH_ID:x86-64 = "9454f7dc-b98e-4a64-a96d-4eb08c7b6e66/da76f9c6bc4276332b587b771243ae34"
SRC_SHA512SUM:x86-64 = "26df0151a3a59c4403b52ba0f0df61eaa904110d897be604f19dcaa27d50860c82296733329cb4a3cf20a2c2e518e8f5d5f36dfb7931bf714a45e46b11487c9a"

SRC_URI[vardeps] += "SRC_FETCH_ID SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://download.visualstudio.microsoft.com/download/pr/${SRC_FETCH_ID}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

DOTNET_RUNTIME = "8.0.1"
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
