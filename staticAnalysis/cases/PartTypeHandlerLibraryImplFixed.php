<?php

$keyArray = [];
$valueArray = [];

function createInstance($string, $instance, $value)
{
    global $keyArray,$valueArray;

    if (!array_key_exists($string, $keyArray)) {
        $keyArray[$string] = [];
        $valueArray[$string] = [];
    } else if(($k = array_search($instance, $keyArray[$string], true)) !== false){
        return $valueArray[$string][$k];
    }
    $keyArray[$string][] = $instance;
    return $valueArray[$string][] = $value;
}
createInstance("test", "test2", "testValue");


