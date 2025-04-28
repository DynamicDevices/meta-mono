require recipes-sato/images/core-image-sato.bb

require core-image-mono.inc

DEFAULT_TEST_SUITES:pn-${PN}:prepend = "dotnet "

# Build up meta-mono test image here
IMAGE_INSTALL += "msbuild \
                  dotnet \
                  dotnet-helloworld \
                  gtk-sharp3 \
                  python3-pythonnet \
"

IMAGE_BASENAME = "${PN}"

