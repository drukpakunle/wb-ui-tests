#!/bin/bash

sh ./version-getter.sh | sed 's/\([0-9\.]\)_.*/\1/'