SUMMARY = "Python for .NET"
DESCRIPTION = "Python.NET is a package that gives Python programmers nearly seamless integration with .NET Framework, .NET Core and Mono runtime on Windows, Linux and macOS."
HOMEPAGE = "http://pythonnet.github.io"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=8e800b2b69ab79d37187ed4eb522060d \
"

inherit pypi python_flit_core

PV = "3.0.4"
SRC_URI[sha256sum] = "c92fbcfddd16575f7e75a643302271658b606d8557df7f0132ac240e03cc3a8f"
PYPI_PACKAGE = "pythonnet"

DOTNET_MIN_REQ_VERSION ?= "6.0.0"

# Python.NET’s documentation - Loading a Runtime
# https://pythonnet.github.io/pythonnet/python.html#loading-a-runtime
# Default runtime is .NET Core (coreclr).
PYTHONNET_DEFAULT_RUNTIME ?= "coreclr"
PYTHONNET_ENV = "#!/bin/bash\n\nexport PYTHONNET_RUNTIME=${PYTHONNET_DEFAULT_RUNTIME}\n"

DEPENDS += " \
    dotnet-native (>= ${DOTNET_MIN_REQ_VERSION}) \
    python3-clr-loader-native \
    python3-setuptools-scm-native \
    python3-toml-native \
"

RDEPENDS:${PN} += " \
    bash \
    dotnet (>= ${DOTNET_MIN_REQ_VERSION}) \
    python3-pycparser \
    python3-clr-loader \
"

# NuGet uses $HOME/.nuget/packages to store packages by default
# but we should not use anything outside the build root of packages.
# Use a separated folder for nuget downloads and cache in UNPACKDIR.
export NUGET_PACKAGES="${UNPACKDIR}/nuget-packages"
export NUGET_HTTP_CACHE_PATH="${UNPACKDIR}/nuget-http-cache"

# Workaround for dotnet restore issue, define custom proxy in a .bbappend
# and/or in layer.conf or local.conf if dotnet restore was failed.
# Override DOTNET_HTTP_PROXY and DOTNET_HTTPS_PROXY in layer.conf or local.conf if needed
DOTNET_HTTP_PROXY ?= ""
DOTNET_HTTPS_PROXY ?= ""
export http_proxy="${DOTNET_HTTP_PROXY}"
export https_proxy="${DOTNET_HTTPS_PROXY}"

do_configure:prepend() {
    if ! grep -Fq __version__ ${S}/pythonnet/__init__.py
    then
        printf "\n__version__ = \"${PV}\"\n" >> ${S}/pythonnet/__init__.py
    fi
}

do_compile[network] = "1"

do_install:prepend() {
    printf "${PYTHONNET_ENV}" > ${UNPACKDIR}/dotnet-env.sh

    install -d ${D}${sysconfdir}/profile.d
    install -m 644 ${UNPACKDIR}/dotnet-env.sh    ${D}${sysconfdir}/profile.d
}
