#! /bin/bash

./clean_base.sh run_magento2.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/magento2\/\(vendor\|dev\)\//d' > run_magento2_clean.csv
