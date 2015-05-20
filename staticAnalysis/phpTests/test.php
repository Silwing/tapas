<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 4/27/15
 * Time: 2:30 PM
 */

$b = $a[1];
$a ++;
++$a;
$a[array_pop($a)] = 1;

$b =        &$a;



function &a ($b){
    if($b){
        return $b;
    }


    $b = 3;

}

