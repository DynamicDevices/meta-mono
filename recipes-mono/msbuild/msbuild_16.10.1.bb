SUMMARY = "The Microsoft Build Engine is a platform for building applications."
HOMEPAGE = "https://docs.microsoft.com/visualstudio/msbuild/msbuild"
SECTION = "console/apps"
LICENSE = "MIT"

DEPENDS = "curl-native ca-certificates-native unzip-native dotnet-native"

RDEPENDS_${PN} = "dotnet"

LIC_FILES_CHKSUM = "file://license;md5=aa2bb45abfacf721bd09860b11b79f5a \
                    file://ref/LicenseHeader.txt;md5=b06c0743af93aeb14a577bb2bfdada8e"

inherit mono

SRCREV = "8463cdd28537acbca482d2a14804f61ab7838383"

SRC_URI = "git://github.com/mono/linux-packaging-msbuild.git;branch=main;protocol=https \
           file://mono-msbuild-license-case.patch \
           file://mono-msbuild-use-bash.patch \
           file://0001-Don-t-try-to-run-pkill.patch \
           file://0001-Copy-hostfxr.patch \
           "

S = "${UNPACKDIR}/git"

do_configure () {
    sed "s|%libhostfxr%|${STAGING_DIR_TARGET}${libdir}/libhostfxr.so|g" -i ${S}/eng/cibuild_bootstrapped_msbuild.sh

    sed "s|\$(HOME)\\\.nuget\\\packages|${NUGET_PACKAGES}|g" -i ${S}/mono/build/common.props
    sed "s|\$(MonoInstallPrefix)\\\lib|${D}${libdir}|g" -i ${S}/mono/build/install.proj
    sed "s|\$(MonoInstallPrefix)\\\bin|${D}${bindir}|g" -i ${S}/mono/build/install.proj
    sed "s|\$(MonoInstallPrefix)\\\share|${D}${datadir}|g" -i ${S}/mono/build/install.proj

    sed "s|'\$1'/bin|${bindir}|g" -i ${S}/mono/build/gen_msbuild_wrapper.sh
    sed "s|\$1/lib|${libdir}|g" -i ${S}/mono/build/gen_msbuild_wrapper.sh
}

export DOTNET_MSBUILD_SDK_RESOLVER_CLI_DIR = "${STAGING_DATADIR_NATIVE}/dotnet"
export CURL_CA_BUNDLE = "${STAGING_DIR_NATIVE}/etc/ssl/certs/ca-certificates.crt"

do_compile[network] = "1"

do_compile () {
    mkdir -p ${UNPACKDIR}/build-home-dir
    export HOME=${UNPACKDIR}/build-home-dir

    # Sync Mono certificate store with ca-certificates
    cert-sync --user ${STAGING_DIR_NATIVE}/etc/ssl/certs/ca-certificates.crt

    # Trust extra sites
    yes | ${STAGING_BINDIR_NATIVE}/certmgr -ssl https://nugetgallery.blob.core.windows.net
    yes | ${STAGING_BINDIR_NATIVE}/certmgr -ssl https://nuget.org
    yes | ${STAGING_BINDIR_NATIVE}/certmgr -ssl https://api.nuget.org
    yes | ${STAGING_BINDIR_NATIVE}/certmgr -ssl https://go.microsoft.com

    ./eng/cibuild_bootstrapped_msbuild.sh --host_type mono --configuration Release --skip_tests /p:DisableNerdbankVersioning=true
}

do_install () {
    export HOME=${UNPACKDIR}/build-home-dir

    ./stage1/mono-msbuild/msbuild mono/build/install.proj /p:MonoInstallPrefix="${D}" /p:Configuration=Release-MONO /p:IgnoreDiffFailure=true
}

FILES:${PN} = "\
    ${bindir} \
    ${libdir}/mono \
"

BBCLASSEXTEND = "native"
