<?php
$a = 1;
$c = $b = &$a;

$a++;

echo $c . " " . $b;