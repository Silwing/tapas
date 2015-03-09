install: install-dependencies
	- mkdir git &&\
	 cd git &&\
	 git clone https://github.com/budde377/Part.git &&\
	 git clone https://github.com/phpmyadmin/phpmyadmin.git &&\
	 git clone https://github.com/mediawiki/mediawiki.git &&\
	 git clone https://github.com/joomla/joomla-cms.git &&\
	 git clone https://github.com/bcit-ci/CodeIgniter.git &&\
	 git clone https://github.com/phpbb/phpbb.git &&\
	 git clone https://github.com/symfony/symfony.git &&\
	 git clone https://github.com/magento/magento2.git &&\
	 git clone https://github.com/zendframework/zf2.git
	- mkdir svn &&\
	 cd svn &&\
	 svn co https://develop.svn.wordpress.org/trunk/

install-dependencies: 
	DEBIAN_FRONTEND=noninteractive sudo apt-get -y install git subversion curl mysql-server ant gawk xvfb firefox

install-php: php5-xdebug php5-imagick php5-curl php5-sqlite \
	 php5-gd php5-mcrypt php-pear php5-dev

install-phpunit:
	wget https://phar.phpunit.de/phpunit.phar
	chmod +x phpunit.phar
	sudo mv phpunit.phar /usr/local/bin/phpunit

install-composer:
	curl -sS https://getcomposer.org/installer | php
	sudo mv composer.phar /usr/local/bin/composer


run-phpunit:  test-code-igniter test-joomla test-mediawiki test-part test-phpbb test-magento2 test-symfony

run-skip-selenium: run-phpunit test-phpmyadmin-no-selenium

run: run test-phpmyadmin

setup: install-phpunit install-dependencies install-composer setup-code-igniter setup-joomla setup-mediawiki setup-part setup-magento2 setup-phpmyadmin setup-zf2 setup-symfony setup-phpbb

setup-code-igniter: 
	cd git/CodeIgniter; composer install

test-code-igniter:
	cd git/CodeIgniter/tests; phpunit

setup-joomla:
	cd git/joomla-cms; composer update

test-joomla: 
	cd git/joomla-cms; phpunit


setup-magento2:
	cd git/magento2; composer install

test-magento2:
	cd git/magento2/dev/tests/unit/; phpunit .

setup-mediawiki-db: db-clean
	mysql -e "GRANT ALL PRIVILEGES ON my_wiki.* TO 'wiki'@'localhost';" -uroot
	mysql -e "CREATE DATABASE IF NOT EXISTS  my_wiki;" -uroot
	mysql -e "SET PASSWORD FOR 'wiki'@'localhost' = PASSWORD('wikia');" -uroot	
	mysql my_wiki -uroot < my_wiki.sql

setup-mediawiki: 
	cd git/mediawiki; composer install; 
	cp LocalSettings.php git/mediawiki/

test-mediawiki:  setup-mediawiki-db
	cd git/mediawiki/tests/phpunit/; make destructive 


test-part: setup-part-db
	cd git/Part; phpunit

setup-part-db: db-clean
	mysql -e "CREATE DATABASE  test_cms;" -uroot
	mysql -e "CREATE DATABASE  test_postfix;" -uroot
	mysql test_postfix -uroot < git/Part/db/setup_postfix.sql
	cd git/Part; make update-db

setup-part: 
	cd git/Part; composer install;


setup-phpbb:
	cd git/phpbb/phpBB; composer install	

test-phpbb:
	cd git/phpbb; phpunit 


test-phpmyadmin: setup-phpmyadmin-db
	- pkill php	
	cd git/phpmyadmin; php --server 127.0.0.1:8000 >php.log 2>php2.log &
	- pkill Xvfb
	- pkill java
	Xvfb :10 -screen 0 1024x768x24 2>/dev/null 1>/dev/null &
	export DISPLAY=:10
	xvfb-run java -jar selenium.jar 2>/dev/null 1>/dev/null & 
	export TESTSUITE_SERVER=localhost;\
	export TESTSUITE_USER=phpmyadmin;\
	export TESTSUITE_PASSWORD=password;\
	export TESTSUITE_DATABASE=test;\
	export TESTSUITE_SELENIUM_HOST=127.0.0.1;\
	export TESTSUITE_SELENIUM_PORT=4444;\
	export TESTSUITE_SELENIUM_BROWSER=firefox;\
	export TESTSUITE_URL=http://localhost:8000;\
	cd git/phpmyadmin;\
	ant clean;\
	ant locales;\
	ant phpunit-nocoverage 

test-phpmyadmin-no-selenium: setup-phpmyadmin-db
	- pkill php	
	cd git/phpmyadmin; php --server 127.0.0.1:8000 >php.log 2>php2.log &
	export TESTSUITE_SERVER=localhost;\
	export TESTSUITE_USER=phpmyadmin;\
	export TESTSUITE_PASSWORD=password;\
	export TESTSUITE_DATABASE=test;\
	export TESTSUITE_URL=http://localhost:8000;\
	cd git/phpmyadmin;\
	ant clean;\
	ant locales;\
	ant phpunit-nocoverage 



setup-phpmyadmin:
	cd git/phpmyadmin; composer install --no-interaction


setup-phpmyadmin-db: db-clean
	mysql -uroot -e "CREATE DATABASE test;"
	mysql -uroot < git/phpmyadmin/sql/create_tables.sql
	mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'phpmyadmin'@'localhost';" -uroot
	mysql -e "SET PASSWORD FOR 'phpmyadmin'@'localhost' = PASSWORD('password');" -uroot	


setup-wordpress-db: db-clean
	mysql -uroot -e "CREATE DATABASE wordpress_tests;" 

setup-wordpress: 
	cd svn/trunk/; \
	cp wp-tests-config-sample.php wp-tests-config.php; \
	sed -i "s/youremptytestdbnamehere/wordpress_tests/" wp-tests-config.php; \
	sed -i "s/yourusernamehere/root/" wp-tests-config.php; \
	sed -i "s/yourpasswordhere//" wp-tests-config.php; \
	svn checkout https://plugins.svn.wordpress.org/wordpress-importer/trunk tests/phpunit/data/plugins/wordpress-importer


test-wordpress: setup-wordpress-db
	cd svn/trunk/; phpunit

setup-symfony:
	cd git/symfony/; composer install

test-symfony:
	cd git/symfony/; phpunit 

setup-zf2:
	cd git/zf2;composer install


test-zf2:
	cd git/zf2; ./tests/run-tests.php

clean:
	rm -rf git svn


db-clean: 
	- mysql -uroot  -e "show databases" | grep -v Database | grep -v mysql| grep -v information_schema | gawk '{print "drop database " $$1 "; "}' | mysql -uroot
