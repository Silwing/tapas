<?php
/**
 * Created by IntelliJ IDEA.
 * User: Silwing
 * Date: 07-05-2015
 * Time: 09:57
 */
$a = 4.5;
$a++;
$b = $a;
$b--;
$c++;
$d--;

$e = [1,2,3];
//$f = $a + $e; // fatal error
$g = $z * $a;
$h = $a / $z;
$i = $a / 0;
$j = 0 / 0;
$k = $f % 2;
$l = 43 % $f;
$m = 2;
$n = pow($m,$f);
$o = pow($f,$m);
$p = pow($f, $f);
$q = $f >= $f;
$r = $m >= $f;
$s = $f >= $m;
$t = -2;
$u = $t >= $f;
$v = $f >= $t;

var_dump($a, $b, $c, $d, $f, $g, $h, $i, $j, $k, $l, $n, $o, $p, $q, $r, $s, $u, $v);