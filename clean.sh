#! /bin/bash

sed -n '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/\(git\|svn\)\//p' $1 |\
sed '/\t\/vagrant\/corpus\/svn\/trunk\/tests\//d' | \
sed '/\t\/vagrant\/corpus\/git\/CodeIgniter\/vendor\//d' | \
sed '/\t\/vagrant\/corpus\/git\/CodeIgniter\/tests\//d' | \
sed '/\t\/vagrant\/corpus\/git\/joomla-cms\/libraries\/vendor\//d'  | \
sed '/\t\/vagrant\/corpus\/git\/joomla-cms\/tests\//d' | \
sed '/\t\/vagrant\/corpus\/git\/Part\/test\//d' | \
sed '/\t\/vagrant\/corpus\/git\/Part\/vendor\//d'
