#! /bin/bash

./clean_base.sh run_joomla.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/joomla-cms\/\(libraries\/vendor\|tests\)\//d' > run_joomla_clean.csv
