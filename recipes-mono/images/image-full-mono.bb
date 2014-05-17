require recipes-sato/images/core-image-sato.bb

require core-image-mono.inc

# Build up complete meta-mono test image here
IMAGE_INSTALL += "mono-upnp"

IMAGE_BASENAME = "${PN}"

