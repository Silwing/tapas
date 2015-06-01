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
$f = 12; // fatal error
$g = $z * $a;
$h = $a / $z;
$i = $a / 0;
$j = 0 / 0;
$k = $f % 2;
$l = 43 % $f;
$m = 2;
$n = $m**$f;
$o = $f**$m;
$p = $f**$f;
$q = $f < $f;
$r = $m < $f;
$s = $f < $m;
$t = -2;
$u = $t < $f;
$v = $f < $t;
$w = 5/2;
$x = -$z;
$aa = $f < null;
$ab = $f < 0;
$ac = null < -42;
$ad = null > 0;
$ae = 0 > null;
$af = null < null;

var_dump(null);
/*$ag = [] > -1;
$ah = [] < -1;
$ai = ["hej"] > -1;
$aj = ["hej"] < -1;
$ak = [1] > -1;
$al = [1] < -1;
$am = [] > [];
$an = ["hej"] > [];
$ao = [1,2] > [1];
$ap = [1] > [1,2];*/
$ba = 100 % 12.5;
$bb = 100 % 12;
$bc = 5 % 2;
$bd = 5 % 2.5;
$be = 4.5 % 1.5;
$bf = 4.5 % 3;
$bg = 100.5 % 25;
$bh = 100.5 % 12.5;
$bj = 0.5;
$bj--;

$ca = -0.8 % 2;
$cb = 5 % -0.2;

