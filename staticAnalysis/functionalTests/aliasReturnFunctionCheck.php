<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 7:37 PM
 */



function &a(&$a) {
    return $a;
}


$a = ["test"];
var_dump($a);
$b = &a($a);
var_dump($b); //Array should be updated
$b[] = 1; //Warning because different type
var_dump($a); //Should be modified $a
var_dump($b); //Should be the same as $b