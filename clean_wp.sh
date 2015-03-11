#! /bin/bash

sed '/\tphar:\/\/\//d' $1 | sed '/\t\/vagrant\/corpus\/svn\/trunk\/tests\//d' | sed '/\t\/usr\/local\/bin\/phpunit/d' 
