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

$g = false;
$h = $g + 4;
$i = null ? 42 : 1337;
$j = $a[null];
$a[null] = "Hmm";
$l = 1;
$k = array_pop($l);

$a[-1] = "Test";
$a["-1"] = "Test2";

var_dump($a, $b,$d, $e, $f, $h, $i, $j, $k, $l);