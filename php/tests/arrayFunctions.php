<?php

$x = [1,2,3];
array_push($x, 4);
rsort($x);
$y = [1,2,3,4,5,6];
$w = ["test" => 2];
$z = array_merge($x,$y,$w);
var_dump($x);
