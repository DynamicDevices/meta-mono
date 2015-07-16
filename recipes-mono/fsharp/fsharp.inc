SUMMARY = "F# programming language"
DESCRIPTION = "F# is a strongly-typed, functional-first programming language for writing simple code to solve complex problems."
SECTION = "devel/mono"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "files://LICENSE;md5=512efb9375da0bd2fee9e2b9352c08af"

inherit autotools-brokensep
inherit mono

# For some reason the URL template changes from version to version,
# therefore use some variables for the directory and extension
SRC_URI = "https://github.com/fsharp/fsharp/archive/${PV}.tar.gz"

FILES_${PN} += "\
  ${libdir}/mono/*/*.optdata \
  ${libdir}/mono/*/*.sigdata \
  ${libdir}/mono/gac/*/*/*.optdata \
  ${libdir}/mono/gac/*/*/*.sigdata \
"
