<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 6:12 PM
 */


$a = [1,2,3];

$a[] = 4;


$a['5'] = 5;

$a['asd'] = 5; //Should give error since $a is list
var_dump($a); //Should be map
$a['asd'] = &$a; //Should give warning since map is to ints

$a[] = 1; //Should give error since $a is now map
var_dump($a); //$a is now top array