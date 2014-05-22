## Introduction

meta-mono is an OpenEmbedded layer that builds mono runtime and mono
libraries to allow users to run .NET applications under linux built
using OE. For more info about mono, see mono project's website. For
more info on OpenEmbedded, see OE's website.

This README pertains to meta-mono layer support for Mono 3.2.8 / 3 4.0

For Mono release notes please see:

* [Mono 3.2 Release Notes](http://www.mono-project.com/Release_Notes_Mono_3.2)
* [Mono 3.4 Release Notes](http://www.mono-project.com/Release_Notes_Mono_3.4)

NOTE: That since Mono 3.2.7 there is initial support for the ARM hardfp ABI 
which should enable us to use hardfp builds of e.g. Yocto/Poky

## Dependencies

On the host:

The host system must have the following packges:
gcc g++ patch diffstat texi2html texinfo cvs subversion gawk
chrpath make libgl1-mesa-dev libglu1-mesa-dev libsdl1.2-dev
git libxml-parser-perl bison gettext flex unzip rpm2cpio

On the target:

So far, Windows Forms applications have been tested under Sato.
Mono does not require Sato, it does require X if you want to run
Windows Forms applications. While mono can run w/o X, that recipe
is not supported currently. It is unlikely to be supported.

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

## Maintainer(s) & Patch policy

* [Alex J Lennon](mailto://ajlennon@dynamicdevices.co.uk)
* [Autif Khan](mailto://autif.mlist@gmail.com), autif on #yocto on freenode

Follow Yocto change submission policy, detailed here:

* [Yocto - How to submit a change](http://www.yoctoproject.org/docs/1.4.2/dev-manual/dev-manual.html#how-to-submit-a-change)

Use the create-pull-request and send-pull-request scripts details in 3.9.1. 

Send to the maintainer and to [Yocto mailing list](mailto://yocto@yoctoproject.org)

## Copyright

MIT/GPLv2 - following the lead of libgdiplus and mono

## Current State - x86: Works

#### 25/02/2014

* Maintainer has tested a Poky build on a qemux86 platform, with a simple console application and a simple Windows Forms application. 

## Current State - ARM vfp: Works

#### 25/02/2014

* Maintainer has tested a Poky build on a Freescale i.MX6 platform, with a simple console application and a simple Windows Forms application. 

## Current State - ARM hardfp: Works

#### 22/04/2014

* Builds and runs on i.MX6 platform with hardfp enabled. 

## Current State - MIPS: Unknown

* More investigation is needed, if there is an interest in getting mono to work perfectly on MIPS, please contact the maintainer.

## Current State - PPC

* More investigation is needed, if there is an interest in getting mono to work perfectly on PPC, please contact the maintainer.

## Current State - alpha, amd64, hppa, ia64, s390 & sparc: Unknown

* When qemu provided by Yocto supports one of these machines, the maintainer will support these architectures.

## TODO

* see if mono works for amd64, seems like qemux86-64 is available
* investigate why windows forms apps dont run on MIPS
* investigate why compilation fails for PPC
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

