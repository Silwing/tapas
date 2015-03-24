#! /bin/bash

./clean_base.sh $1 '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/mediawiki\/\(vendor\|tests\)\//d'
