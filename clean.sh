#! /bin/bash

sed '/\tphar:\/\/\//d' $1 | sed '/\t\/vagrant\/corpus\/git\/CodeIgniter\/vendor\//d' | sed '/\t\/usr\/local\/bin\/phpunit/d' 
