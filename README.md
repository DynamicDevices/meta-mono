## Introduction

meta-mono is an OpenEmbedded layer that builds dotNet, the mono runtime and mono libraries to allow users to run .NET applications under linux built using OE. For more info about mono, see mono project's website. For more info on OpenEmbedded, see OE's website.

## Build status

| Branch | Support Status* | Status of Build & Tests |
| ------ | --------------- | ----------------------- |
| nanbield | 	May 2024  | [![nanbield](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=nanbield&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| mickledore | 	EOL  | [![mickledore](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=mickledore&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| langdale | 	EOL  | [![langdale](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=langdale&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| kirkstone | 	Long Term Support (minimum Apr. 2024)	 | [![master](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=kirkstone&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| honister | EOL | [![honister](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=honister&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| hardknott | EOL | [![hardknott](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=hardknott&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| gatesgarth | EOL | [![gatesgarth](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=gatesgarth&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| dunfell | 	Supported - Long Term Support (until Apr. 2024) | [![dunfell](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=dunfell&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| zeus | EOL | [![zeus](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=zeus&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| warrior | EOL | [![warrior](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=warrior&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| thud | EOL | [![thud](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=thud&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| sumo | EOL | [![sumo](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=sumo&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |
| morty | EOL | [![sumo](https://img.shields.io/github/actions/workflow/status/dynamicdevices/meta-mono/CI_github.yml?branch=morty&label=build%20%26%20test)](https://github.com/DynamicDevices/meta-mono/actions/workflows/CI_github.yml) |

*support status as of 22/02/24, follows main Yocto release support schedule [here](https://wiki.yoctoproject.org/wiki/Releases)

NOTE: 24/01/22 - This [patch](https://patchwork.openembedded.org/patch/171349/) is needed to Poky for branch morty, sumo (and perhaps others) to fix a gcc version build error.

## Layer Dependencies

This layer depends on:

URI: git://git.openembedded.org/openembedded-core
layers: meta
branch: master

## Detail

For Mono release notes please see [here](https://www.mono-project.com/docs/about-mono/releases)

## Layer Dependencies

The libgdiplus recipe has a soft dependency on the giflib recipe which is provided by the meta-oe layer.
These dependencies can be controlled using the PACKAGECONFIG feature of yocto.  libgdiplus
recognizes the following options:

"jpeg exif gif tiff"

These can be controlled via the .bbappend mechanism or by adding:
PACKAGECONFIG_pn-mono = ""
PACKAGECONFIG_pn-mono-native = ""

directives to the local.conf

If this recipe is included in an image with gif enabled, for example when building core-image-mono, then
conf/bblayers needs to be modified to include meta-oe, e.g.

BBLAYERS = " \
  ..
  /path/to/meta-oe \
  ..
"

Otherwise an error of the following form will be seen:

ERROR: Required build target 'core-image-mono' has no buildable providers. Missing or unbuildable dependency chain was: ['core-image-mono', 'mono-helloworld', 'mono', 'libgdiplus', 'giflib']

## Other Dependencies

On the host:

The host system must have the following packges:
gcc g++ patch diffstat texi2html texinfo cvs subversion gawk
chrpath make libgl1-mesa-dev libglu1-mesa-dev libsdl1.2-dev
git libxml-parser-perl bison gettext flex unzip rpm2cpio

On the target:

So far, Windows Forms applications have been tested under Sato.
Mono does not require Sato, it does require X if you want to run
Windows Forms applications. 

## Testing Mono builds

The meta-mono layer now includes basic image test support.

For details on configuring image For setup details see: https://wiki.yoctoproject.org/wiki/Image_tests

i.e.

- Add INHERIT += "testimage" in local.conf
- bitbake core-image-mono
- bitbake core-image-mono -c testimage

This will run some simple tests in Qemu (ensure MACHINE is configured appropriately)

- helloworld (command line executable)
- helloworldform (WinForms executable)
- helloworldgtk (GTK# executable

Python tests are defined in lib/oeqa/runtime/cases/mono.py

Currently only core-image-mono is working.

## Build Examples

There are mono-helloworld and a mono-helloworld-xbuild recipes in recipes-mono

These show two ways of building C# projects for deployment to the target. Each downloads a tarball release from a git repository, although they could just as easily download and checkout a commit from the git repository.

mono-helloworld then implements the recommended autotools files for build with autoconf. This is based on the Mono example, mono-skel.

For a walkthrough on building .NET recipes from first principles see:

* [Building and running embedded Linux .NET applications from first principles](https://wiki.yoctoproject.org/wiki/Building_and_running_embedded_Linux_.NET_applications_from_first_principles)

For details see: 

* [Mono Application Deployment Guidelines](http://mono-project.com/Guidelines:Application_Deployment)

mono-helloworld-xbuild implements a Visual Studio 2010 solution and projects which allow us to checkout and build using Visual Studio on a Windows host, or alternatively using Mono xbuild within a Yocto/OpenEmbedded recipe.

For a walkthrough on building Mono for Raspberry Pi see:

* [Getting Mono Running on a Raspberry Pi Using Yocto](http://www.codeproject.com/Articles/840489/Getting-Mono-Running-on-a-Raspberry-Pi-Using-Yocto)

## CI Setup

The current CI system runs in a ProxMox container supporting nested Docker operation. A couple of specific settings are needed to support QEMU testing (/dev/net/tun, /dev/kvm) and the lxc configuration file is documented here for future reference

```
arch: amd64
cores: 4
features: keyctl=1,mknod=1,nesting=1
hostname: github-runner
memory: 24576
net0: name=eth0,bridge=vmbr2,firewall=1,gw=10.21.21.254,hwaddr=1A:EC:5A:0D:A3:B8,ip=10.21.21.100/32,type=veth
onboot: 1
ostype: debian
rootfs: lvm:vm-101-disk-0,size=1T
swap: 8192
unprivileged: 1
lxc.cgroup2.devices.allow: c 10:200 rwm
lxc.mount.entry: /dev/net dev/net none bind,create=dir
lxc.cgroup2.devices.allow: c 10:232 rwm
lxc.mount.entry: /dev/kvm dev/kvm none bind,create=file 0 0
```

## Contributors

* Alex J Lennon
* Autif Khan
* Chris Morgan
* Enric Balletbo i Serra
* Khem Raj
* Richard Tollerton
* Fabio Berton
* Barry Grussling
* Zoltán Böszörményi
* Ioan-Adrian Ratiu

## Maintainer(s) & Patch policy

* [Alex J Lennon](mailto:ajlennon@dynamicdevices.co.uk)

Follow Yocto change submission policy, detailed here, for formatting:

* [Yocto - How to submit a change](http://www.yoctoproject.org/docs/1.4.2/dev-manual/dev-manual.html#how-to-submit-a-change)

* Create an issue on github.com/dynamicdevices/meta-mono
* Fork master on github.com/dynamicdevices/meta-mono
* Commit your changes to your fork referencing the issue number prefixed with #
* Create a pull request (PR) to the main repository
* Your PR will trigger a number of CI builds which will run tests to ensure nothing is broken. The CI builds must pass for your PR to be merged by a maintainer. If your PR does not build correctly please address and update the PR with a fix.
* If the maintainer doesn't respond after a reasonable time email them a reminder

## Copyright

MIT/GPLv2 - following the lead of libgdiplus and mono

## Hit Count

[![HitCount](http://hits.dwyl.com/dynamicdevices/meta-mono.svg)](http://hits.dwyl.com/dynamicdevices/meta-mono)
