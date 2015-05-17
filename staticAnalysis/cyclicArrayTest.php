<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/11/15
 * Time: 12:47 PM
 */

function doStuffWithFancyArray($a, $i){
    if($i == 10){
        return;
    }

    $a[$i] = "x";
    doStuffWithFancyArray($a, $i+1);
}


$a = [1,2,3];
$b = 1;

$a[] = &$a;

doStuffWithFancyArray($a, 0);

$a[] = 4;