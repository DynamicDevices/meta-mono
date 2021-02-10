SUMMARY = "Build system for .NET projects - unmanaged helper library"
HOMEPAGE = "https://github.com/dotnet/core-setup"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.TXT;md5=9fc642ff452b28d62ab19b7eea50dfb9"

COMPATIBLE_HOST ?= "(i.86|x86_64|arm|aarch64).*-linux"

SRC_URI = "git://github.com/dotnet/core-setup.git;branch=release/3.1 \
           file://0001-Don-t-set-a-plethora-of-compiler-arguments-through-c.patch;patchdir=${WORKDIR}/git \
           file://0002-Remove-broken-objcopy-detection-STRIP_SYMBOLS-is-uns.patch;patchdir=${WORKDIR}/git \
           "
SRCREV = "f5eceb810586ea6138aadcef9e2bba115015ab99"

inherit cmake

S = "${WORKDIR}/git/src/corehost"

def get_dotnet_arch(bb, d, arch_var):
    import re
    a = d.getVar(arch_var)
    if   re.match(r'(i.86|athlon|x86.64)$', a):         return 'I386'
    elif re.match(r'arm$', a):                          return 'ARM'
    elif re.match(r'armeb$', a):                        return 'ARM'
    elif re.match(r'aarch64$', a):                      return 'ARM64'
    elif re.match(r'aarch64_be$', a):                   return 'ARM64'
    else:
        raise bb.parse.SkipRecipe("Cannot map '%s' to a supported dotnet architecture" % a)

def get_dotnet_host_arch(bb, d):
    return get_dotnet_arch(bb, d, 'HOST_ARCH')

def get_dotnet_target_arch(bb, d):
    return get_dotnet_arch(bb, d, 'TARGET_ARCH')

EXTRA_OECMAKE = " \
    -DCLI_CMAKE_HOST_VER:STRING=${PV} \
    -DCLI_CMAKE_COMMON_HOST_VER:STRING=${PV} \
    -DCLI_CMAKE_HOST_FXR_VER:STRING=${PV} \
    -DCLI_CMAKE_HOST_POLICY_VER:STRING=${PV} \
    -DCLI_CMAKE_PKG_RID:STRING=linux \
    -DCLI_CMAKE_COMMIT_HASH:STRING=${SRCREV} \
"

EXTRA_OECMAKE_append_class-native = " \
    -DCLI_CMAKE_PLATFORM_ARCH_${@get_dotnet_host_arch(bb, d)}=1 \
"

EXTRA_OECMAKE_append_class-target = " \
    -DCLI_CMAKE_PLATFORM_ARCH_${@get_dotnet_target_arch(bb, d)}=1 \
"

do_install() {
	install -d ${D}${libdir}/mono/msbuild/Current/bin/SdkResolvers/Microsoft.DotNet.MSBuildSdkResolver
	install -m755 ${B}/cli/fxr/libhostfxr.so ${D}${libdir}/mono/msbuild/Current/bin/SdkResolvers/Microsoft.DotNet.MSBuildSdkResolver/libhostfxr.so
}

FILES_${PN} = " \
    ${libdir}/mono/msbuild/Current/bin/SdkResolvers/Microsoft.DotNet.MSBuildSdkResolver/libhostfxr.so \
"

BBCLASSEXTEND = "native"
