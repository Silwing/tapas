<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/21/15
 * Time: 9:09 AM
 */

$a = [1,2,3];
$b = $a;
$c = &$a[0];
$c = "test";
var_dump($b[0]);
var_dump($a[0]);
//Both arrays are joined even though in practice $b is not updated