<?php
$arr = [1,2,3];
$notArr = "hej";
$notArr2 = true;
$notArr3 = null;
$notArr4 = 42;

$results = [array_merge($arr,$notArr), array_merge($arr, $notArr2), array_merge($arr,$notArr3), array_merge($arr,$notArr4)];

var_dump($results);