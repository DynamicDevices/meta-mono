DESCRIPTION = "Implements a generic interface for loading one of the CLR (.NET) runtime implementations and calling simple functions on them."
HOMEPAGE = "http://pythonnet.github.io"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cdef1cb9133877183afac105849a771e"

inherit python_flit_core

CLR_LOADER_VERSION = "0.2.4"
PV = "${CLR_LOADER_VERSION}+git${SRCPV}"
SRC_URI = "git://github.com/pythonnet/clr-loader.git;protocol=https;branch=master;tag=v${CLR_LOADER_VERSION}"

DOTNET_MIN_REQ_VERSION ?= "6.0.0"

DEPENDS += " \
    dotnet-native (>= ${DOTNET_MIN_REQ_VERSION}) \
    ${PYTHON_PN}-setuptools-scm-native \
    ${PYTHON_PN}-toml-native \
"

RDEPENDS:${PN} += " \
    dotnet (>= ${DOTNET_MIN_REQ_VERSION}) \
    ${PYTHON_PN}-cffi \
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
    if ! grep -Fq __version__ ${S}/clr_loader/__init__.py
    then
        printf "\n__version__ = \"${CLR_LOADER_VERSION}\"\n" >> ${S}/clr_loader/__init__.py
    fi
}

do_compile[network] = "1"

do_compile:prepend() {
    python3 setup.py build_dotnet
    cp -R ${S}/build/lib/clr_loader/ffi/dlls    ${S}/clr_loader/ffi/
}

BBCLASSEXTEND = "native"
