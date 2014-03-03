require dbus-sharp.inc

inherit pkgconfig

SRC_URI += "file://fix-message-reader-writer.patch"

SRC_URI[md5sum] = "b63c684b326a20119cdefdddb1fa0578"
SRC_URI[sha256sum] = "efbe1ab0c519efe80fe3498a5836b5d754af87f9b3cff3bf0cbb16d5fc7e0a50"

