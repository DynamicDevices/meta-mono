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

# AJL - Could split out mdb debugging files into -dbg package in future?
FILES_${PN} += "\
  ${libdir}/mono/* \  
  ${libdir}/mono/xbuild/Microsoft/VisualStudio/* \
  ${libdir}/mono/Microsoft F#/v4.0/* \
  ${libdir}/mono/Microsoft SDKs/F#/3.0/* \
  ${libdir}/mono/Microsoft SDKs/F#/3.1/* \
  ${libdir}/mono/2.0/* \
  ${libdir}/lib/mono/gac/* \
  ${libdir}/mono/gac/FSharp.Compiler.Interactive.Settings/4.3.1.0__f536804aa0eb945b/* \
  ${libdir}/mono/gac/FSharp.Compiler.Server.Shared/4.3.1.0__f536804aa0eb945b/* \
  ${libdir}/mono/gac/FSharp.Data.TypeProviders/4.3.1.0__f536804aa0eb945b/* \
  ${libdir}/mono/gac/policy.2.3.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/policy.2.3.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/policy.4.0.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/policy.3.3.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/policy.3.3.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/policy.2.0.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Core/2.3.98.1__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Core/2.3.1.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Core/4.3.1.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Core/2.3.99.1__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Compiler/4.3.1.0__f536804aa0eb945b/* \
  ${libdir}/mono/gac/policy.4.3.FSharp.Core/0.0.0.0__b03f5f7f11d50a3a/* \
  ${libdir}/mono/gac/FSharp.Build/* \
  ${libdir}/mono/4.0/* \
  ${libdir}/mono/monotouch/* \
  ${libdir}/mono/monodroid/* \
"

