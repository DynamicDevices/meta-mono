require mono-git.inc
require ${PN}-base.inc

do_compile() {
    make get-monolite-latest
    make EXTERNAL_MCS="${S}/mcs/class/lib/monolite/basic.exe" EXTERNAL_RUNTIME="${S}/foo/bar/mono"
}

