require recipes-sato/images/core-image-sato.bb

require core-image-mono.inc

# Build up complete meta-mono test image here
IMAGE_INSTALL += "mono-upnp \
		  dbus-sharp \
		  dbus-sharp-glib \ 
		  gtk-sharp \
		  mono-helloworld \
		  mono-xsp \
		  monotools-server \
		  taglib-sharp \
		  fsharp \
		  libgdiplus \ 
		  mono-basic \
		  mono-upnp \
"

IMAGE_BASENAME = "${PN}"

