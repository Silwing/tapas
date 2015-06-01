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

$b = fib($n);

$c = fib(10);
var_dump($a);//Should be top number
