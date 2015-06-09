<?php

$keyArray = [];
$valueArray = [];

function createInstance($string, $instance, $value)
{
    global $keyArray,$valueArray;

    if (!array_key_exists($string, $keyArray)) {
        $keyArray[$string] = [];
        $valueArray[$string] = [];
    } else if(($k = array_search($instance, $keyArray, true)) !== false){
        return $valueArray[$k];
    }
    $keyArray[] = $instance;
    return $valueArray[] = $value;
}
createInstance("test", "test2", "testValue");


