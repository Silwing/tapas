<?php

$x = [5,10,15];
$y = [1, 5 => 7, 10];
$z = ["hej","med","dig"];
$w = ["ord1" => "hej", "ord2" => "med", "ord3" => "dig"];
$a = [1,2,3, "hej" => "verden"];

$v = $x[0] . " " . $y[0] . " " . $z[0] . " " . $w["ord1"] . " " . $a[0];

echo $v . "\n";
