<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/13/15
 * Time: 9:54 AM
 */

$c = [[1,2,3]];

$c[1]["asd"] = 123;

$c = [[1,2,3], [1=>2]];

$c[1]["asd"] = 123;


$d = [[1,2,3]];

$d[] = ["as"=>2];
$d[] = 2;
$d[0] = "";

