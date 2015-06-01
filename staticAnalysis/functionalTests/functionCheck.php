<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 7:37 PM
 */



function a($a) {
    return $a;
}


$b = a(1);
var_dump($b); // Should be 1
$c = a(2);
var_dump($c); //Should be 2
