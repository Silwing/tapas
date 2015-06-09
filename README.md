# TAPAs: Type Analysis for PHP Arrays
This repository contains the source code for the dynamic and static analyses in our Master's Thesis.

* **root** - Vagrantfile and bootstrap.sh can be used to recreate the environment used for running the dynamic analysis. clean_*.sh files are used to clean log files resulting from running the dynamic analysis
* **corpus** - Running make in this directory will download the corpus frameworks and run the dynamic analysis 
* **php/tests** - small PHP scripts used for testing the logging features of [our modified PHP interpreter](http://github.com/Silwing/php-src)
* **plugin** - source code for the static analysis implemented as an IntelliJ IDEA plugin
* **python** - python scripts used to process the log files from running the dynamic analysis
* **staticAnalysis** - PHP scripts used for the static analysis separated into tests of how PHP works, tests cases for the analysis and functional tests of the analysis
