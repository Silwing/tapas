#! /bin/bash

./clean_base.sh run_symfony.csv '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/symfony\/\(vendor\/\|src\/Symfony\/\(\(Bundle\|Bridge\|Component\(\/[a-zA-Z]\+\)\?\)\/[a-zA-Z]\+\/Tests\/\)\)/d' > run_symfony_clean.csv
