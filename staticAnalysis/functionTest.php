<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 4/22/15
 * Time: 11:05 AM
 */

$a = 123;

function test(&$a, $b, $c){
    $result = null;
    $a[] = 123;
    $a[123] = 123;
    return $result;
}
test($a,2,3);


if(1){
    $a = 1;
}  else {
    $a = 2;
}







