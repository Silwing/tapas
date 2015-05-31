<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/5/15
 * Time: 5:28 PM
 */

$a = 1;


var_dump($a);
$z = $a++; //Should be UintTop
var_dump($a);
$y = $a--; //Should be top number due to weak update of heap
var_dump($a);
$x = ++$a; //Should be top number due to weak update of heap
var_dump($a);
$v = --$a; //Should be top number due to weak update of heap
var_dump($a);
