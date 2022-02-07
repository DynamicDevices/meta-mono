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
    # FIX: Morty's BitBake wants the second argument for getVar() function which specifies
    # when the build variable is expanded. True expands it immediately which is necessary for the following test.
    a = d.getVar(arch_var, True)
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
    -DCLI_CMAKE_HOST_VER=${PV} \
    -DCLI_CMAKE_COMMON_HOST_VER=${PV} \
    -DCLI_CMAKE_HOST_FXR_VER=${PV} \
    -DCLI_CMAKE_HOST_POLICY_VER=${PV} \
    -DCLI_CMAKE_PKG_RID=linux \
    -DCLI_CMAKE_COMMIT_HASH=${SRCREV} \
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

do_install_append_class-native () {
	install -m755 ${B}/cli/fxr/libhostfxr.so ${D}${libdir}
}

FILES_${PN} = " \
    ${libdir}/mono/msbuild/Current/bin/SdkResolvers/Microsoft.DotNet.MSBuildSdkResolver/libhostfxr.so \
"

#
# This overrides process_file_linux function defined in chrpath.bbclass.
# The original functin does not work well with libhostfxr library because its RPATH contains many '.'
# entries which effectively consume space in the RPATH and modified RPATH variable is then too long.
# This fixed version checks if the path already exists in RPATH variable and appends it only if it is 
# not present yet.
#
def process_file_linux(cmd, fpath, rootdir, baseprefix, tmpdir, d):
    import subprocess as sub

    p = sub.Popen([cmd, '-l', fpath],stdout=sub.PIPE,stderr=sub.PIPE)
    out, err = p.communicate()
    # If returned successfully, process stdout for results
    if p.returncode != 0:
        return

    out = out.decode('utf-8')

    # Handle RUNPATH as well as RPATH
    out = out.replace("RUNPATH=","RPATH=")
    # Throw away everything other than the rpath list
    curr_rpath = out.partition("RPATH=")[2]
    bb.note("Current rpath for %s is %s" % (fpath, curr_rpath.strip()))
    rpaths = curr_rpath.split(":")
    new_rpaths = []
    modified = False
    for rpath in rpaths:
        # If rpath is already dynamic copy it to new_rpath and continue
        if rpath.find("$ORIGIN") != -1:
            # FIX: Check for duplicite entries before adding new item:
            if rpath.strip() not in new_rpaths:
                new_rpaths.append(rpath.strip())
            continue
        rpath =  os.path.normpath(rpath)
        if baseprefix not in rpath and tmpdir not in rpath:
            # FIX: Check for duplicite entries before adding new item:
            if rpath.strip() not in new_rpaths:
                new_rpaths.append(rpath.strip())
            continue
        new_rpaths.append("$ORIGIN/" + os.path.relpath(rpath.strip(), os.path.dirname(fpath.replace(rootdir, "/"))))
        modified = True

    # if we have modified some rpaths call chrpath to update the binary
    if modified:
        args = ":".join(new_rpaths)
        bb.note("Setting rpath for %s to %s" %(fpath, args))
        p = sub.Popen([cmd, '-r', args, fpath],stdout=sub.PIPE,stderr=sub.PIPE)
        out, err = p.communicate()
        if p.returncode != 0:
            bb.fatal("%s: chrpath command failed with exit code %d:\n%s%s" % (d.getVar('PN', True), p.returncode, out, err))

BBCLASSEXTEND = "native"

