<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 9:08 AM
 */


function &a(&$b){
    return $b;
}

$a = 123;
$b = a($a);
var_dump($b);
$b = 1;
var_dump($b); //Should be uint
var_dump($a); //Should be 123

