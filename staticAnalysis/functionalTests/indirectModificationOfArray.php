<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/19/15
 * Time: 12:52 PM
 */


$a = [];

$b = &$a[0];

$b = $a;

var_dump($a); //Should be cyclic
var_dump($a[0]); //Should be the same (and null)