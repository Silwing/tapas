<?php
/**
 * Created by IntelliJ IDEA.
 * User: Randi
 * Date: 02-06-2015
 * Time: 14:26
 */

$arr = [];
$a = 3.14;
$arr["$a"] = "test2";
$arr[strval($a)] = "test";
$arr[$a] = "test3";

var_dump($arr);