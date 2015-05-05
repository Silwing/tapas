<?php
/**
 * Created by IntelliJ IDEA.
 * User: Randi
 * Date: 05-05-2015
 * Time: 19:38
 */

$a = ["Hej", "Verden", "C"];
$b = (int)$a;
$c = [];
$d = (int)$c;
$e = (bool)$a;
$f = (bool)$c;

var_dump($b,$d, $e, $f);