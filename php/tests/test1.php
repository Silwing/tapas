<?php

$x = [1,2,3];
echo $x[0] . "\n";
$k = 1;
$v = $x[$k];
echo $v . "\n";
$y = [2,3,4];
$v = $y[$k];
echo $v . "\n";
$y[1.5] = "hej";
var_dump($y);
$v = $y[true];
echo $v . "\n";
$z = ["test" => 1, "hej" => [1,2,3], 5 => 42];
echo $z["test"] . "\n";
$z[] = "woop";
echo $z[5] . "\n" . $z[6] . "\n";
?>
