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


$a = fib(1); //Should be 1
var_dump($a);//Should be top number
$b = fib($n); //Should be null
var_dump($b);//Should be top number
$c = fib(10);
var_dump($c);//Should be top number
