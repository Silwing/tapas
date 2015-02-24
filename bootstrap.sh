#!/usr/bin/env bash

apt-get update

# setup git and clone php-src
apt-get install -y git
mkdir -p /vagrant/php/src
mkdir /vagrant/php/tests
cd /vagrant/php/src
git clone https://github.com/Silwing/php-src .
git checkout PHP-5.6.6
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
apt-get build-dep -y php5
