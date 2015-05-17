<?php
/**
 * Created by IntelliJ IDEA.
 * User: Randi
 * Date: 17-05-2015
 * Time: 22:01
 */

function createInstance($string, $instance, $value)
{
    static $keyArray = [];
    static $valueArray = [];
    if (!isset($keyArray[$string])) {
        $keyArray[$string] = [];
        $valueArray[$string] = [];
    } else if (($k = array_search($instance, $keyArray[$string], true)) !== false) {
        return $valueArray[$string][$k];
    }
    $keyArray[$string][] = $instance;
    return $valueArray[$string][] = $value;
}

createInstance("test", "test2", "testValue");