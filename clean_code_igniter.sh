#! /bin/bash

./clean_base.sh run_code_igniter.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/CodeIgniter\/\(vendor\|tests\)\//d' > run_code_igniter_clean.csv
