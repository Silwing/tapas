#! /bin/bash

./clean_base.sh $1 '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/zf2\/\(vendor\|tests\)\//d'
