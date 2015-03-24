<?php

$notAnArray = 5;
$stillNotArray = null;
$totallyNotArray = "Hello World";
$nopeNotArray = new StdClass();

$res = [];
$res[] = array_search(null, $notAnArray);
$res[] = array_search(null, $stillNotArray);
$res[] = array_search(null, $totallyNotArray);
$res[] = array_search(null, $nopeNotArray);

echo $res[0];
