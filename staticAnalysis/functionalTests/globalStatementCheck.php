<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 7:37 PM
 */



function a() {
    global $a;
    return $a;
}


$b = a();
var_dump($b); // Should be NULL
$a = 1;
$c = a();
var_dump($c); //Should be 1
