<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/7/15
 * Time: 11:35 AM
 */


function test(){

    $a = &$_POST; //Should be from global scope
    $b = &$_GET;//Should be from global scope
    $c = &$_SERVER; //Should be from global scope
    $d = &$_SESSION; //Should be from global scope
    $e = &$GLOBALS; //Should be from global scope
    $f = &$_REQUEST; //Should be from global scope
    $g = &$_ENV; //Should be from global scope
    $h = &$_COOKIE; //Should be from global scope
    $_POST = 1;
}


test();

//POST should be 1
