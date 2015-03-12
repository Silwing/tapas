<?php
class Hest {
	public $legs = 4;
}

$x = new StdClass();
$x->greet = "Hello World";

$y = new Hest();
$y->legs = 3;

$a = [];
$a[] = $x;
$a[] = $y;
echo $a[0]->greet . " " . $a[1]->legs . "\n";
