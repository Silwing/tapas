<?php

$arr = [];

array_push($arr, "test");
$arr[] = "val2";
$arr[2] = "val3";

array_pop($arr);
array_shift($arr);

$val = $arr[0];

echo $val . PHP_EOL;
