#! /bin/bash

./clean_base.sh $1 '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/symfony\/\(vendor\/\|src\/Symfony\/\(\(Bundle\|Bridge\|Component\(\/[a-zA-Z]\+\)\?\)\/[a-zA-Z]\+\/Tests\/\)\)/d'
