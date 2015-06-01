<?php
/**
 * Created by PhpStorm.
 * User: budde
 * Date: 5/31/15
 * Time: 4:39 PM
 */

$a = 2;

$b = 123;

$a = &$b; //Should be hard updated

var_dump($a);