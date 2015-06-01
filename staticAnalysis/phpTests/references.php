<?php
$a = 1;
$c = $b = &$a;

$a++;

var_dump( $c . " " . $b);