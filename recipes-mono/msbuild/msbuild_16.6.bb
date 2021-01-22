SUMMARY = "The Microsoft Build Engine is a platform for building applications."
HOMEPAGE = "https://docs.microsoft.com/visualstudio/msbuild/msbuild"
SECTION = "console/apps"
LICENSE = "MIT"

DEPENDS = "unzip-native dotnet"

RDEPENDS_${PN} = "dotnet"

LIC_FILES_CHKSUM = "file://license;md5=aa2bb45abfacf721bd09860b11b79f5a \
                    file://ref/LicenseHeader.txt;md5=b06c0743af93aeb14a577bb2bfdada8e"

inherit mono

SRCREV = "94d0c55bc96f297618d50cc32167ddba9fee30b0"

SRC_URI = "git://github.com/mono/linux-packaging-msbuild.git \
           file://mono-msbuild-dotnetbits-case.patch \
           file://mono-msbuild-license-case.patch \
           file://mono-msbuild-no-hostfxr.patch \
           file://mono-msbuild-use-bash.patch \
           file://0001-Don-t-try-to-run-pkill.patch \
           file://0001-Copy-hostfxr.patch \
           file://0002-Remove-myget-feeds-and-replace-with-AzDO-feeds.patch \
           "

S = "${WORKDIR}/git"

do_configure () {
    sed "s|%libhostfxr%|${STAGING_DIR_TARGET}${libdir}/libhostfxr.so|g" -i ${S}/eng/cibuild_bootstrapped_msbuild.sh

    sed "s|\$(HOME)\\\.nuget\\\packages|${NUGET_PACKAGES}|g" -i ${S}/mono/build/common.props
    sed "s|\$(MonoInstallPrefix)\\\lib|${D}${libdir}|g" -i ${S}/mono/build/install.proj
    sed "s|\$(MonoInstallPrefix)\\\bin|${D}${bindir}|g" -i ${S}/mono/build/install.proj
    sed "s|\$(MonoInstallPrefix)\\\share|${D}${datadir}|g" -i ${S}/mono/build/install.proj

    sed "s|'\$1'/bin|${bindir}|g" -i ${S}/mono/build/gen_msbuild_wrapper.sh
    sed "s|\$1/lib|${libdir}|g" -i ${S}/mono/build/gen_msbuild_wrapper.sh
}

export DOTNET_MSBUILD_SDK_RESOLVER_CLI_DIR="${STAGING_DATADIR_NATIVE}/dotnet"
export CURL_CA_BUNDLE="${STAGING_DIR_NATIVE}/etc/ssl/certs/ca-certificates.crt"

do_compile () {
    ./eng/cibuild_bootstrapped_msbuild.sh --host_type mono --configuration Release --skip_tests /p:DisableNerdbankVersioning=true
}

do_install () {
    ./stage1/mono-msbuild/msbuild mono/build/install.proj /p:MonoInstallPrefix="${D}" /p:Configuration=Release-MONO /p:IgnoreDiffFailure=true
}

FILES_${PN} = "\
    ${bindir} \
    ${libdir}/mono \
"

BBCLASSEXTEND = "native"
