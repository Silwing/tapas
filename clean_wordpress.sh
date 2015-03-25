#! /bin/bash

./clean_base.sh run_wordpress.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/svn\/trunk\/tests\//d' > run_wordpress_clean.csv
