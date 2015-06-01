<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/5/15
 * Time: 5:42 PM
 */



$a = true || 0; //Should be true
var_dump($a);
$b = true && $a; //Should be true
var_dump($b);