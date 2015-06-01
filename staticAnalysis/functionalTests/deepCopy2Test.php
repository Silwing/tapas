<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/21/15
 * Time: 9:09 AM
 */

$a = [1,2,3];
$c = &$a[0];
$b = $a;
$c = "test";
var_dump($b[0]);
var_dump($a[0]);
//Both arrays are joined This is what happens in practice