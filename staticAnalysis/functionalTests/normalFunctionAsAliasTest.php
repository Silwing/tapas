<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 9:14 AM
 */

function a($a){
    return $a;
}


$a = 123;
$b = &a($a);
$b = 1;
var_dump($b); //Should be uint
var_dump($a); //Should be 123