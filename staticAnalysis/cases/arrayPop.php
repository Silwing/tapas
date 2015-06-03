<?php

$people = ["John", "Jane", "Alice", "Bob"];
$animals = ["Dog", "Cat", "Bird", "Fish"];
$animalMap = [];

for($i = 0; $i < count($people); $i++) {
    $animalMap[$people[$i]] = $animals[$i];
    $animalMap[$i] = ", ";
}

array_pop($animalMap);
$animalString = implode("", $animalMap);
echo "The people have the following animals: " . $animalString;