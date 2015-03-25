#! /bin/bash

./clean_base.sh run_phpmyadmin.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/phpmyadmin\/\(vendor\|test\)\//d' > run_phpmyadmin_clean.csv
