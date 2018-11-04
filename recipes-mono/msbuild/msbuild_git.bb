SUMMARY = "The Microsoft Build Engine is a platform for building applications."
HOMEPAGE = "https://github.com/mono/msbuild"
SECTION = "console/apps"
LICENSE = "MIT"
DEPENDS = " \
			coreutils-native findutils-native curl-native \
			unzip-native mono-native \
		"

LIC_FILES_CHKSUM = "file://LICENSE;md5=768c552053144071f8ef7e5621714b0a"

inherit mono

PV = "15.4"

SRCREV = "1822f8c9b6b63753c2498493a7c0522a9d263ca6"

SRC_URI = " \
			git://github.com/mono/msbuild.git;protocol=https;branch=d${PV} \
			file://cibuild.sh-debug.patch \
		"

S = "${WORKDIR}/git"

do_compile () {
	./cibuild.sh --scope Compile --host Mono --target Mono --config Release --bootstrap-only
}

do_install () {
	MONO_PREFIX=${prefix}
	DESTDIR="${D}"

	install -d -m0755 ${D}${libdir}/mono
	DESTDIR="${D}" ./install-mono-prefix.sh ${prefix}

	# Extract some variables from ./install-mono-prefix.sh
	XBUILD_DIR=$(grep ^XBUILD_DIR= ./install-mono-prefix.sh | sed s,XBUILD_DIR=\$MONO_PREFIX,,)
	MSBUILD_TOOLSVERSION=$(grep ^MSBUILD_TOOLSVERSION ./install-mono-prefix.sh | sed s,MSBUILD_TOOLSVERSION=,,)
	MSBUILD_INSTALL_BIN_DIR=$MONO_PREFIX/lib/mono/msbuild/$MSBUILD_TOOLSVERSION/bin

	# Set explicitly and re-do the whole installation manually
	MSBUILD_OUT_DIR="bin/Release-MONO/AnyCPU/Unix/Unix_Deployment"
	install $MSBUILD_OUT_DIR/*.* ${D}$MSBUILD_INSTALL_BIN_DIR/

	# Some conditional copies from install-mono-prefix.sh
	# must be performed manually here because the separated
	# build and installed trees in Yocto / Bitbake.
	test -d ${STAGING_DIR_HOST}${prefix}$XBUILD_DIR/14.0/Imports && cp -R ${STAGING_DIR_HOST}${prefix}$XBUILD_DIR/14.0/Imports ${D}${prefix}$XBUILD_DIR/$MSBUILD_TOOLSVERSION
	# End of conditional copies from install-mono-prefix.sh

	sed -i -es,${D},,g ${D}${bindir}/msbuild

	# There are two conflicting files in these directories
	# across msbuild and mono 5.12.x
	rm -rf ${D}${libdir}/mono/xbuild/15.0/Microsoft.Common.targets
	rm -rf ${D}${libdir}/mono/xbuild/15.0/Imports/Microsoft.Common.props
}

do_install_append_class-target() {
	install -d -m0755 ${D}${libdir}/mono/msbuild/15.0/bin/Roslyn
	install -m0755 ${S}/bin/Release-MONO/AnyCPU/Unix/Unix_Deployment/Roslyn/* ${D}${libdir}/mono/msbuild/15.0/bin/Roslyn/
}

FILES_${PN} += " \
			${libdir}/mono/ \
		"

SYSROOT_PREPROCESS_FUNCS += "msbuild_sysroot_preprocess"

msbuild_sysroot_preprocess () {
	install -d ${SYSROOT_DESTDIR}${bindir_crossscripts}

	install -m0755 ${S}/msbuild-mono-deploy.in ${SYSROOT_DESTDIR}${bindir_crossscripts}/msbuild
	sed -i -es,@bindir@,${STAGING_BINDIR_NATIVE}, ${SYSROOT_DESTDIR}${bindir_crossscripts}/msbuild
	sed -i -es,@mono_instdir@,${STAGING_LIBDIR}/mono, ${SYSROOT_DESTDIR}${bindir_crossscripts}/msbuild
}

BBCLASSEXTEND = "native"
