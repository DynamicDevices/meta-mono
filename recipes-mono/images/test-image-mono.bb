require recipes-sato/images/core-image-sato.bb

require core-image-mono.inc

DEFAULT_TEST_SUITES:pn-${PN}:prepend = "dotnet "

# Build up meta-mono test image here
IMAGE_INSTALL += "msbuild \
                  dotnet \
                  dotnet-helloworld \
"

# Required for qemu testimage (serial/ssh root login) on wrynose+.
IMAGE_FEATURES += "allow-empty-password empty-root-password allow-root-login"

IMAGE_BASENAME = "${PN}"

