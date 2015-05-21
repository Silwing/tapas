<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/6/15
 * Time: 10:17 AM
 */


$b = 1;

function a() {
    global $a;
}


$b = a(); // Should be NULL

function b(){
    return 1;
}

$c = b(); //Should be 1



function &c(&$a){
    return $a;
}

$d[$a] = c($c); //Should be 1 on unique reference