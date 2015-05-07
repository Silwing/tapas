<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/7/15
 * Time: 12:00 PM
 */



function fib($i){
    if($i <= 1){
        return $i;
    }
    return fib($i - 1) + fib($i -2);
}


/*

$a = fib(1); //Should be 1
$a = fib($n); //Should be 1*/
$a = fib(10);