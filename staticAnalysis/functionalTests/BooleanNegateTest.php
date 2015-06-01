<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 4/23/15
 * Time: 3:17 PM
 */

$a = !1; //Should be *false*
var_dump($a);
$b = !0; //Should be *true*
var_dump($b);
$c = !$a; //Should be *true*
var_dump($c);