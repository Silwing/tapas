<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/2/15
 * Time: 10:05 AM
 */

function f(){
    global $a;
    $a = 123;
}

f();
var_dump($a); //Should be 123