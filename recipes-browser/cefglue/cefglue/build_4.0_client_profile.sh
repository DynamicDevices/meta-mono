#!/bin/sh

xbuild "$@"  /property:Configuration=Release /property:TargetFrameworkVersion=v4.0 /property:TargetFrameworkProfile=Client build.proj
