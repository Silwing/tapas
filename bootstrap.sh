#!/usr/bin/env bash

apt-get update

# setup git and clone php-src
apt-get install -y git

if [ -d "/vagrant/php/src" ]; then 
    echo "Folder exists, will not clone from git"
    cd /vagrant/php/src
else     
    mkdir -p /vagrant/php/src
    cd /vagrant/php/src
    git clone https://github.com/Silwing/php-src .
    git checkout PHP-5.6.6

fi

git config merge.NEWS.name "Keep the NEWS file"
git config merge.NEWS.driver 'touch %A'
git config merge.log true
git config alias.co checkout
git config color.ui true
git config --add merge.ff false
git config --global push.default current

# install build tools for php
apt-get install -y build-essential autoconf automake libtool re2c
mkdir /vagrant/php/deps
cd /vagrant/php/deps
wget http://launchpadlibrarian.net/140087283/libbison-dev_2.7.1.dfsg-1_amd64.deb
wget http://launchpadlibrarian.net/140087282/bison_2.7.1.dfsg-1_amd64.deb
dpkg -i libbison-dev_2.7.1.dfsg-1_amd64.deb
dpkg -i bison_2.7.1.dfsg-1_amd64.deb
#rm libbison-dev_2.7.1.dfsg-1_amd64.deb
#rm bison_2.7.1.dfsg-1_amd64.deb
apt-mark hold libbison-dev
apt-mark hold bison
apt-get install -f -y

# install php dependencies

DEBIAN_FRONTEND=noninteractive apt-get -y build-dep php5


# install php

apt-get install libmcrypt4 libmcrypt-dev

cd /vagrant/php/src

./buildconf --force 
./custom_conf
make -j1 
make install 

cp /vagrant/php/src/php.ini-production /usr/local/lib/php.ini

pear config-set php_ini /usr/local/lib/php.ini
pecl config-set php_ini /usr/local/lib/php.ini 

# install php xdebug

pecl install xdebug

# install php imagick

apt-get install -y libmagickwand-dev
printf "\n" | pecl install imagick
