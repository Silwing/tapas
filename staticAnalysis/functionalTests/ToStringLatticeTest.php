<?php
/**
 * Created by IntelliJ IDEA.
 * User: Silwing
 * Date: 06-05-2015
 * Time: 11:12
 */

$a = [];
$b = ["Hej"];
$c = 5.2;
$d = true;
$e = false;
$f = null;

$g = $a . $b . $c . $d . $e . $f;

var_dump($g); //Should be "ArrayArray5.21"