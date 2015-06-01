<?php

$monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
$monthMap = [];

for($i = 1; $i <= 12; $i++) {
	var_dump($monthMap);
	$monthMap[array_pop($monthNames)] = $i;
	var_dump($monthMap);
}
var_dump($monthMap);
$input = $_GET["monthName"];

echo $input . " is month number " . $monthMap[$input];