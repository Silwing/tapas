<?php

$size = pow(2, 16); // any power of 2 will do

$startTime = microtime(true);

// Insert keys [0, $size, 2 * $size, 3 * $size, ..., ($size - 1) * $size]

$array = array();
for ($key = 0, $maxKey = ($size - 1) * $size; $key <= $maxKey; $key += $size) {
    $array[$key] = 0;
}

$endTime = microtime(true);

printf("Inserted %d elements in %.2f seconds\n", $size, $endTime - $startTime);
