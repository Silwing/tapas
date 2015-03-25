#! /bin/bash

./clean_base.sh run_zf2.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/zf2\/\(vendor\|tests\)\//d' > run_zf2_clean.csv
