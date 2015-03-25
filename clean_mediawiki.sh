#! /bin/bash

./clean_base.sh run_mediawiki.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/mediawiki\/\(vendor\|tests\)\//d' > run_mediawiki_clean.csv
