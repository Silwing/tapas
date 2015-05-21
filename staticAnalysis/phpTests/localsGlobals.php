<?php
$a = 42;

function test() {
    global $a;
    $a = 43;
    return $a;
}

echo test() . PHP_EOL . $a;