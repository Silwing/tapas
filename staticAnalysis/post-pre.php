<?php
function &counter() {
    static $c = 0;
    return $c;
}

$a = &counter();
$a++;

echo counter();