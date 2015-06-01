<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 11:05 AM
 */


$c = [1=>1, 2=>2, 3=>3]; //Is list



$g = $h = &$c;

$c["test"] = &$a; //Converting $c to map


$d = array_pop($c); //Error since $c is map
$d = array_pop($g); //Correct since $g is still list.
$d = array_pop($h); //Error since $h is alias of $c and therefor is map