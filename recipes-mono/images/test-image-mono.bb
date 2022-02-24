require recipes-sato/images/core-image-sato.bb

require core-image-mono.inc

# Build up meta-mono test image here
IMAGE_INSTALL += "msbuild \
                  dotnet \
"

IMAGE_BASENAME = "${PN}"

