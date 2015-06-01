<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/11/15
 * Time: 12:47 PM
 */

$a = [1,2,3];
$a[] = &$a;

var_dump($a); //Should be list with four locations
$a[3] = 1;
var_dump($a); //Should update all locations to include 1 and add new location.