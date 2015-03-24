#! /bin/bash

sed -n '/^\(hash_init\|[a-z_]\+\t[0-9]\+\t\/vagrant\/corpus\/\(git\|svn\)\/\)/p' $1 |\
sed $2
