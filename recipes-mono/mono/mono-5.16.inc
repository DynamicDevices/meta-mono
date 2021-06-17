# The source package erroneously contains some build output files from a
# build for x86. These need to be deleted else we can't cross-compile the
# package.
#
# Clenaup is based on mono commit ffa8c9992f8 ("Merge pull request #11509 from
# directhex/cleanup-system-native-make-dist")

do_configure_prepend () {
    rm -rf $(find ${S} -type d -name "*\.libs")
    rm -rf $(find ${S} -name "*\.deps")
    rm -f $(find ${S} -name "*\.o")
    rm -f $(find ${S} -name "*\.so")
    rm -f $(find ${S} -name "*\.lo")
    rm -f $(find ${S} -name "*\.Plo")
    rm -f $(find ${S} -name "\.dirstamp")
}
