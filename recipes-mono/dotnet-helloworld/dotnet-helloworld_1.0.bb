SUMMARY = "Dotnet Hello World"
DESCRIPTION = "Test applications for dotnet console"
AUTHOR = "Hichem, Ben Fekih<hichem.f@live.de>"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS:append = " dotnet-native"

RDEPENDS:${PN}:append = " \
    icu \
    libgssapi-krb5 \
    zlib \
"

COMPATIBLE_HOST ?= "(x86_64|aarch64|arm).*-linux"

SRC_ARCH:aarch64 = "arm64"
SRC_ARCH:arm = "arm"
SRC_ARCH:x86-64 = "x64"

INSANE_SKIP:${PN} += "\
    already-stripped \
    staticdev \
"

S = "${WORKDIR}/src"

do_compile[network] = "1"

do_compile () {
    dotnet new console --force -o ${S} --name ${PN}
    dotnet build ${S}/${PN}.csproj --output ${B}/${PN} --configuration release --runtime linux-${SRC_ARCH}

    #FIXME: remove the following line. if the lttng-ust conflict is solved
    rm ${B}/${PN}/libcoreclrtraceptprovider.so
}

do_install () {
    install -d ${D}/opt/
    cp -r --no-preserve=ownership ${B}/${PN} ${D}/opt

    if [ "${SRC_ARCH}" = "x64" ]; then
        ln -s ${base_libdir} ${D}/lib64
    fi
}

FILES:${PN}:append = " /opt/${PN}/ /lib64"