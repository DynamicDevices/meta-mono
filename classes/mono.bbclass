# Class for building C# packages. If your package is all-managed, add
# PACKAGE_ARCH="all"

DEPENDS += "mono-native mono"
RDEPENDS_${PN} += "mono"

FILES_${PN} += "\
  ${libdir}/mono/*/*.exe \
  ${libdir}/mono/*/*.dll \
  ${libdir}/mono/*/*.config \
  ${libdir}/mono/gac/*/*/*.dll \
  ${libdir}/mono/gac/*/*/*.*.config \
"

FILES_${PN}-dbg += "\
  ${libdir}/mono/*/*.mdb \
  ${libdir}/mono/gac/*/*/*.mdb \
"

FILES_${PN}-dev += "\
  ${libdir}/mono/*/*.rsp \
  ${libdir}/mono/*/*.xml \
  ${libdir}/mono/gac/*/*/*.xml \
  ${libdir}/mono/xbuild/* \
  ${libdir}/mono/xbuild-frameworks/* \
  ${libdir}/mono/Microsoft* \
  ${libdir}/mono/*/*.Targets \
"

FILES_${PN}-doc += "\
  ${libdir}/monodoc/* \
"

export MONO_CFG_DIR="${STAGING_ETCDIR_NATIVE}"
