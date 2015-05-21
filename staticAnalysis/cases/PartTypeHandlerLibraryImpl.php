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
    } else if(($k = array_search($instance, $keyArray, true)) !== false){
        return $this->valueArray[$k];
    }
    $keyArray[] = $instance;
    return $valueArray[] = $value;
}

createInstance("test", "test2", "testValue");