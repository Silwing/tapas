<?php

$x = [1,2,3];
array_push($x, 4);
rsort($x);
$y = [1,2,3,4,5,6];
$w = ["test" => 2];
$z = array_merge($x,$y,$w);
$a = ["id", "name", "age"];
$b = [2, "Randi", "24"];
var_dump(array_combine($a, $b));
var_dump($x);
