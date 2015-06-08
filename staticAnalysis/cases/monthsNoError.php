<?php

$monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
$monthMap = [];

for($i = 1; $i <= 12; $i++) {
	$monthMap[array_pop($monthNames)] = $i;
}
$input = $_GET["monthName"];

echo $input . " is month number " . $monthMap[$input];