SUMMARY = "F# programming language"
DESCRIPTION = "F# is a strongly-typed, functional-first programming language for writing simple code to solve complex problems."
SECTION = "devel/mono"
DEPENDS = "mono"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "files://LICENSE;md5=512efb9375da0bd2fee9e2b9352c08af"

inherit autotools

# For some reason the URL template changes from version to version,
# therefore use some variables for the directory and extension
SRC_URI = "https://github.com/fsharp/fsharp/archive/${PV}.tar.gz"

FILES_${PN} += "\
  ${libdir}/mono/* \  
  ${libdir}/mono/4.0/* \
  ${libdir}/mono/2.0/* \
  ${libdir}/mono/xbuild/Microsoft/VisualStudio/* \
  ${libdir}/mono/Microsoft F#/v4.0/* \
  ${libdir}/mono/Microsoft SDKs/F#/3.0/* \
  ${libdir}/mono/Microsoft SDKs/F#/3.1/* \
  ${libdir}/mono/gac/* \
  ${libdir}/mono/gac/*/* \
  ${libdir}/mono/gac/*/*/* \
  ${libdir}/mono/monotouch/* \
  ${libdir}/mono/monodroid/* \
"

FILES_${PN}-dbg += "\
  ${libdir}/mono/*.mdb \  
  ${libdir}/mono/4.0/*.mdb \
  ${libdir}/mono/4.0/*.optdata \
  ${libdir}/mono/4.0/*.sigdatadata \
  ${libdir}/mono/2.0/*.mdb \
  ${libdir}/mono/2.0/*.optdata \
  ${libdir}/mono/2.0/*.sigdata \
  ${libdir}/mono/xbuild/Microsoft/VisualStudio/*.mdb \
  ${libdir}/mono/Microsoft F#/v4.0/*.mdb \
  ${libdir}/mono/Microsoft SDKs/F#/3.0/*.mdb \
  ${libdir}/mono/Microsoft SDKs/F#/3.1/*.mdb \
  ${libdir}/mono/gac/*.mdb \
  ${libdir}/mono/gac/*/*.mdb \
  ${libdir}/mono/gac/*/*/*.mdb \
  ${libdir}/mono/gac/*/*/*.xml \
  ${libdir}/mono/gac/*/*/*.optdata \
  ${libdir}/mono/gac/*/*/*.sigdata \
  ${libdir}/mono/monotouch/*.mdb \
  ${libdir}/mono/monotouch/*.xml \
  ${libdir}/mono/monotouch/*.sigdata \
  ${libdir}/mono/monotouch/*.optdata \
  ${libdir}/mono/monodroid/*.mdb \
  ${libdir}/mono/monodroid/*.xml \
  ${libdir}/mono/monodroid/*.optdata \
  ${libdir}/mono/monodroid/*.sigdata \
"

