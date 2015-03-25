#! /bin/bash

./clean_base.sh run_phpbb.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/phpbb\/\(phpBB\/vendor\|tests\)\//d' > run_phpbb_clean.csv
