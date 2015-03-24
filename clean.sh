#! /bin/bash

sed -n '/^\(hash_init\|[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/\(git\|svn\)\/\)/p' $1 |\
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/svn\/trunk\/tests\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/CodeIgniter\/\(vendor\|tests\)\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/joomla-cms\/\(libraries\/vendor\|tests\)\//d'  | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/Part\/\(test\|vendor\)\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/magento2\/\(vendor\|dev\)\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/symfony\/\(vendor\/\|src\/Symfony\/\(\(Bundle\|Bridge\|Component\(\/[a-zA-Z]\+\)\?\)\/[a-zA-Z]\+\/Tests\/\)\)/d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/zf2\/\(vendor\|tests\)\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/phpmyadmin\/\(vendor\|test\)\//d' | \
sed '/^[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/git\/phpbb\/\(phpBB\/vendor\|tests\)\//d' 


