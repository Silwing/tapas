#! /bin/bash

./clean_base.sh run_part.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/Part\/\(test\|vendor\)\//d' > run_part_clean.csv
