
do_configure_append () {
	rm -rf ${S}/external/corefx/src/Native/Unix/System.Native/{.libs/*,*.{o,lo}}
}
