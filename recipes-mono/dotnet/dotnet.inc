DESCRIPTION = ".NET Core Runtime, SDK & CLI tools"
HOMEPAGE = "https://www.microsoft.com/net/core"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9fc642ff452b28d62ab19b7eea50dfb9"

COMPATIBLE_HOST ?= "(x86_64|aarch64|arm).*-linux"

DEPENDS = "patchelf-native"

#FIXME add lttng-ust as soon as dotnet core supports liblttng-ust.so.1
RDEPENDS:${PN}:class-target = "icu zlib libgssapi-krb5"
RDEPENDS:${PN}:class-native = "icu zlib"
RDEPENDS:${PN}:class-nativesdk = "icu zlib"
RDEPENDS:${PN}-dev = "zlib"

PR = "r0"

SRC_ARCH:aarch64 = "arm64"
SRC_ARCH:arm = "arm"
SRC_ARCH:x86-64 = "x64"

SRC_URI[vardeps] += "SRC_ARCH"
SRC_URI[sha512sum] = "${SRC_SHA512SUM}"

SRC_URI = "https://builds.dotnet.microsoft.com/dotnet/Sdk/${PV}/dotnet-sdk-${PV}-linux-${SRC_ARCH}.tar.gz;subdir=dotnet-${PV}"

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

    # Install ALL host/fxr versions from the SDK tarball.  Microsoft ships
    # updated muxer binaries in serviced SDK releases (e.g. SDK 6.0.428 may
    # bundle a 10.0.0 muxer), so only copying DOTNET_RUNTIME_VERSION breaks
    # the muxer's fxr lookup.
    install -d ${D}${datadir}/dotnet/host/fxr
    cp -r --no-preserve=ownership ${S}/host/fxr/* ${D}${datadir}/dotnet/host/fxr/

    cp -r --no-preserve=ownership ${S}/packs ${D}${datadir}/dotnet/
    cp -r --no-preserve=ownership ${S}/sdk ${D}${datadir}/dotnet/
    cp -r --no-preserve=ownership ${S}/sdk-manifests ${D}${datadir}/dotnet/

    install -d ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME_VERSION} ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App

    install -d ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App
    cp -r --no-preserve=ownership ${S}/shared/Microsoft.AspNetCore.App/${DOTNET_RUNTIME_VERSION} ${D}${datadir}/dotnet/shared/Microsoft.AspNetCore.App

    # Hack to fix liblttng-ust dependency issues
    patchelf --remove-needed liblttng-ust.so.0 ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME_VERSION}/libcoreclrtraceptprovider.so
    patchelf --remove-needed liblttng-ust.so.1 ${D}${datadir}/dotnet/shared/Microsoft.NETCore.App/${DOTNET_RUNTIME_VERSION}/libcoreclrtraceptprovider.so

    # Symlink libhostfxr.so - prefer DOTNET_RUNTIME_VERSION, fall back to
    # whatever the SDK tarball actually ships (muxer version may differ).
    install -d ${D}${libdir}
    if [ -f ${D}${datadir}/dotnet/host/fxr/${DOTNET_RUNTIME_VERSION}/libhostfxr.so ]; then
        ln -rs ${D}${datadir}/dotnet/host/fxr/${DOTNET_RUNTIME_VERSION}/libhostfxr.so ${D}${libdir}/libhostfxr.so
    else
        for fxr_dir in ${D}${datadir}/dotnet/host/fxr/*/; do
            if [ -f "${fxr_dir}libhostfxr.so" ]; then
                ln -rs "${fxr_dir}libhostfxr.so" ${D}${libdir}/libhostfxr.so
                break
            fi
        done
    fi
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
    ${datadir}/dotnet/packs \
    ${datadir}/dotnet/sdk \
    ${datadir}/dotnet/sdk-manifests \
    ${datadir}/dotnet/templates \
"

FILES:${PN}-dbg = "\
    ${datadir}/dotnet/.debug \
"

RRECOMMENDS:dotnet-dev[nodeprrecs] = "1"

INSANE_SKIP:${PN} = "already-stripped libdir staticdev textrel dev-so"
INSANE_SKIP:${PN}-dev = "libdir staticdev dev-elf"
INSANE_SKIP:${PN}-dbg = "libdir"

# For native builds, replace the dotnet symlink with a wrapper script that
# ensures HOME is always writable.  .NET SDK 6 NuGet MigrationRunner.Run()
# unconditionally writes to $HOME/.local/share/NuGet/Migrations *before*
# checking DOTNET_SKIP_FIRST_TIME_EXPERIENCE.  In CI containers HOME is
# often read-only, breaking every recipe that invokes dotnet.  The wrapper
# fixes this for ALL consumer recipes automatically.
do_install:append:class-native() {
    rm -f ${D}${bindir}/dotnet
    cat > ${D}${bindir}/dotnet << 'WRAPPER'
#!/bin/sh
export HOME="${DOTNET_CLI_HOME:-${TMPDIR:-/tmp}/dotnet-home}"
mkdir -p "$HOME" 2>/dev/null || true
export DOTNET_SKIP_FIRST_TIME_EXPERIENCE=true
export DOTNET_CLI_TELEMETRY_OPTOUT=1
export DOTNET_NOLOGO=1
SELF_DIR="$(cd "$(dirname "$0")" && pwd)"
exec "${SELF_DIR}/../share/dotnet/dotnet" "$@"
WRAPPER
    chmod 0755 ${D}${bindir}/dotnet
}

BBCLASSEXTEND = "native nativesdk"
