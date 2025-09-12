# Class for building C# packages. If your package is all-managed, add
# PACKAGE_ARCH="all"

DEPENDS += "mono-native ca-certificates-native mono"
RDEPENDS:${PN} += "mono"

FILES:${PN}:append = " \
  ${libdir}/mono/*/*.exe \
  ${libdir}/mono/*/*.dll \
  ${libdir}/mono/*/*.config \
  ${libdir}/mono/gac/*/*/*.dll \
  ${libdir}/mono/gac/*/*/*.*.config \
"

FILES:${PN}-dbg:append = " \
  ${libdir}/mono/*/*.mdb \
  ${libdir}/mono/gac/*/*/*.mdb \
"

FILES:${PN}-dev:append = " \
  ${libdir}/mono/*/*.rsp \
  ${libdir}/mono/*/*.xml \
  ${libdir}/mono/gac/*/*/*.xml \
  ${libdir}/mono/xbuild/* \
  ${libdir}/mono/xbuild-frameworks/* \
  ${libdir}/mono/Microsoft* \
  ${libdir}/mono/*/*.Targets \
"

FILES:${PN}-doc:append = " \
  ${libdir}/monodoc/* \
"

export MONO_CFG_DIR = "${STAGING_ETCDIR_NATIVE}"

# NuGet uses $HOME/.nuget/packages to store packages by default
# but we should not use anything outside the build root of packages.
export NUGET_PACKAGES = "${UNPACKDIR}/mono-nuget-packages"
export NUGET_HTTP_CACHE_PATH = "${UNPACKDIR}/mono-nuget-http-cache"

do_configure:prepend() {
	mkdir -p ${NUGET_PACKAGES} ${NUGET_HTTP_CACHE_PATH}
	cert-sync --user ${STAGING_DIR_NATIVE}/etc/ssl/certs/ca-certificates.crt
}
