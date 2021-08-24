## Introduction

meta-mono is an OpenEmbedded layer that builds mono runtime and mono
libraries to allow users to run .NET applications under linux built
using OE. For more info about mono, see mono project's website. For
more info on OpenEmbedded, see OE's website.

## Layer Dependencies

This layer depends on:

URI: git://git.openembedded.org/openembedded-core
layers: meta
branch: master

## Detail

This README pertains to meta-mono layer support for Mono 6.8.x - 6.12.x

For Mono release notes please see:

* [Mono 6.8.0 Release Notes](https://www.mono-project.com/docs/about-mono/releases/6.8.0/)

NOTE: Mono TLS vulnerabilities. 3.12.1+ includes the fix in the release archive.

http://www.mono-project.com/news/2015/03/07/mono-tls-vulnerability/

NOTE: ARM hardfp support

Since Mono 3.2.7 there is initial support for the ARM hardfp ABI which should 
enable us to use hardfp builds of e.g. Yocto/Poky. However there are issues with 
using this, namely exceptions when using Windows Forms components. For details see:

https://bugzilla.xamarin.com/show_bug.cgi?id=20239

For now the recommendation is to use softfp only when using Mono.

There may be a solution to this issue in Mono 3.10.0 / 3.12.0+ but this is as yet untested.
Feedback on testing with armhf would be appreciated and incorporated into this README.

## Layer Dependencies

The libgdiplus recipe has a soft dependency on the giflib recipe which is provided by the meta-oe layer.
These dependencies can be controlled using the PACKAGECONFIG feature of yocto.  libgdiplus
recognizes the following options:

"jpeg exif gif tiff"

These can be controlled via the .bbappend mechanism or by adding:
PACKAGECONFIG:pn-mono = ""
PACKAGECONFIG:pn-mono-native = ""

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

These show two ways of building C# projects for deployment to the target. Each
downloads a tarball release from a git repository, although they could just as
easily download and checkout a commit from the git repository.

mono-helloworld then implements the recommended autotools files for build with
autoconf. This is based on the Mono example, mono-skel.

For a walkthrough on building .NET recipes from first principles see:

* [Building and running embedded Linux .NET applications from first principles](https://wiki.yoctoproject.org/wiki/Building_and_running_embedded_Linux_.NET_applications_from_first_principles)

For details see: 

* [Mono Application Deployment Guidelines](http://mono-project.com/Guidelines:Application_Deployment)

mono-helloworld-xbuild implements a Visual Studio 2010 solution and projects 
which allow us to checkout and build using Visual Studio on a Windows host, 
or alternatively using Mono xbuild within a Yocto/OpenEmbedded recipe.

For a walkthrough on building Mono for Raspberry Pi see:

* [Getting Mono Running on a Raspberry Pi Using Yocto](http://www.codeproject.com/Articles/840489/Getting-Mono-Running-on-a-Raspberry-Pi-Using-Yocto)

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
* If the maintainer doesn't respond after a reasonable time email them a reminder

## Copyright

MIT/GPLv2 - following the lead of libgdiplus and mono

## Current State - x86 emulation: Works

#### 14/06/2018

5.12.0.226

* Maintainer has tested Poky (pyro) build on a qemux86 platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

#### 30/05/2017

4.8.1.0

* Maintainer has tested Poky (master, morty) build on a qemux86 platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

#### 28/07/2015

3.12.1

* Maintainer has tested a Poky build on a qemux86 platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

## Current State - PPC emulation: Works

#### 22/12/2016

4.8.0.382

* Maintainer has tested a Poky (master) build on a qemuppc platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application
  (Not convinced that the test applications are exiting reliably)

## Current State - ARM emulation: Works

#### 14/06/2018

5.12.0.226

* Maintainer has tested a Poky build on a qemuarm platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

#### 28/07/2015

4.0.3.19

* Maintainer has tested a Poky build on a qemuarm platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

#### 28/07/2015

3.12.1

* Maintainer has tested a Poky build on a qemuarm platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

## Current State - ARM vfp: Works

4.6.1

* Has been tested on Cortex-A9

#### 27/04/2014

4.0.1

* Maintainer has tested a Poky build on a Freescale i.MX6 platform with console helloworld and Matchbox UI helloworldform

#### 09/03/2015

3.12.1

* Maintainer has tested a Poky build on a Freescale i.MX6 platform with console helloworld and Matchbox UI helloworldform

#### 25/02/2014

* Maintainer has tested a Poky build on a Freescale i.MX6 platform, with a simple console application and a simple Windows Forms application. 

## Current State - ARM hardfp: Works / Issues with Windows Forms components

#### 22/04/2014

* Builds and runs on i.MX6 platform with hardfp enabled. 

## Current State - MIPS: Unknown

* More investigation is needed, if there is an interest in getting mono to work perfectly on MIPS, please contact the maintainer.

## Current State - PPC

#### 25/06/2017

5.4.0.56

* Maintainer has tested a Poky (master) build on a qemuppc platform, with  

a simple console application, helloworld which RUNS
a simple Windows Forms application, helloworldform which RUNS
a simple GTK# application hellloworldgtk, which RUNS

#### 01/07/2017

5.2.0.196

* Maintainer has tested a Poky (master) build on a qemuppc platform, with  

a simple console application, helloworld which RUNS
a simple Windows Forms application, helloworldform which RUNS
a simple GTK# application hellloworldgtk, which RUNS

#### 14/02/2016

4.2.0.179

* Maintainer has tested a Poky (Jethro) build on a qemuppc platform, with 

a simple console application, helloworld which RUNS
a simple Windows Forms application, helloworldform which FAILS
a simple GTK# application hellloworldgtk, which RUNS

#### 14/02/2016

4.0.1.34

* Maintainer has tested a Poky (Jethro) build on a qemux86 platform, with a simple console application, a simple Windows Forms application, and a simple GTK# application

#### 14/02/2016

3.12.1

Fails to link building Poky (Jethro) with:

| mono/3.12.1-r0/mono-3.12.1/mono/metadata/loader.c:2322: undefined reference to `mono_sigctx_to_monoctx'

## Current State - alpha, amd64, hppa, ia64, s390 & sparc: Unknown

* When qemu provided by Yocto supports one of these machines, the maintainer will support these architectures.

## TODO

* see if mono works for amd64, seems like qemux86-64 is available
* investigate why windows forms apps dont run on MIPS
* run mono runtime tests for ARM
* run mono runtime tests for MIPS
* run mono runtime tests for PPC
* run mono library tests for ARM
* run mono library tests for MIPS
* run mono library tests for PPC
* write recipes to create packages for runtime tests
* write recipes to create packages for mscorlib library tests
* write recipes to create packages for System.dll library tests
* try mono on beagleboard (email maintainer when you do this, he will thank you)

## HitCount

[![HitCount](http://hits.dwyl.com/dynamicdevices/meta-mono.svg)](http://hits.dwyl.com/dynamicdevices/meta-mono)
