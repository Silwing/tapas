<?php
$greeting = "Hello";
$suffix = " World";
for($i = 0; $i < strlen($suffix); $i++) {
	$greeting[strlen($greeting)] = $suffix[$i];
}
echo $greeting . "\n";
