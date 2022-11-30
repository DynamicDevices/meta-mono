DESCRIPTION = "Python.NET is a package that gives Python programmers nearly seamless integration with .NET Framework, .NET Core and Mono runtime on Windows, Linux and macOS."
HOMEPAGE = "http://pythonnet.github.io"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8e800b2b69ab79d37187ed4eb522060d"

inherit python_flit_core

PYTHONNET_VERSION = "3.0.1"
PV = "${PYTHONNET_VERSION}+git${SRCPV}"
SRC_URI = "git://github.com/pythonnet/pythonnet.git;protocol=https;branch=master;tag=v${PYTHONNET_VERSION}"

DOTNET_MIN_REQ_VERSION ?= "6.0.0"

# Python.NET’s documentation - Loading a Runtime
# https://pythonnet.github.io/pythonnet/python.html#loading-a-runtime
# Default runtime is .NET Core (coreclr).
PYTHONNET_DEFAULT_RUNTIME ?= "coreclr"
PYTHONNET_ENV = "#!/bin/bash\n\nexport PYTHONNET_RUNTIME=${PYTHONNET_DEFAULT_RUNTIME}\n"

DEPENDS += " \
    dotnet-native (>= ${DOTNET_MIN_REQ_VERSION}) \
    ${PYTHON_PN}-clr-loader-native \
    ${PYTHON_PN}-setuptools-scm-native \
    ${PYTHON_PN}-toml-native \
"

RDEPENDS:${PN} += " \
    bash \
    dotnet (>= ${DOTNET_MIN_REQ_VERSION}) \
    ${PYTHON_PN}-pycparser \
    ${PYTHON_PN}-clr-loader \
"

S = "${WORKDIR}/git"

# NuGet uses $HOME/.nuget/packages to store packages by default
# but we should not use anything outside the build root of packages.
# Use a separated folder for nuget downloads and cache in WORKDIR.
export NUGET_PACKAGES="${WORKDIR}/nuget-packages"
export NUGET_HTTP_CACHE_PATH="${WORKDIR}/nuget-http-cache"

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
        printf "\n__version__ = \"${PYTHONNET_VERSION}\"\n" >> ${S}/pythonnet/__init__.py
    fi
}

do_compile[network] = "1"

do_compile:prepend() {
    python3 setup.py build_dotnet
    cp -r ${S}/build/lib/pythonnet/runtime/Python.Runtime.*     ${S}/pythonnet/runtime/
}

do_install:prepend() {
    cwd=$(pwd)
    printf "${PYTHONNET_ENV}" > ${WORKDIR}/dotnet-env.sh

    install -d ${D}${sysconfdir}/profile.d
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}

    install -m 644 ${S}/clr.py                 ${D}${PYTHON_SITEPACKAGES_DIR}
    install -m 644 ${WORKDIR}/dotnet-env.sh    ${D}${sysconfdir}/profile.d

    cd ${D}${PYTHON_SITEPACKAGES_DIR}
    python3 -m py_compile clr.py
    cd ${cwd}
}
