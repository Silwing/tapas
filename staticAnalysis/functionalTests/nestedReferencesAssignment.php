<?php
$a = 1;
$c = $b = &$a;

$a++;

var_dump($c); //Should be 1
var_dump($b); //Should be Uint