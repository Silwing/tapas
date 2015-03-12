<?php

$a = [];
$a[0] = 1;
$a[1] = new StdClass();
$a[2] = "Hej";
$a[3] = $a[0];
$a[4] = true;
array_push($a, 5);
array_push($a, new StdClass());
array_push($a, $a[4]);
