<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/12/15
 * Time: 12:40 PM
 */


$a = [1,2,3];

$b = array_pop($a);

$v  = $a["test"];

$a['asd'] = 1;

$b = array_pop($a);


$c = [1=>1, 2=>2, 3=>3];



$g = $h = &$c;

$c["test"] = &$a;


$d = array_pop($c);
$d = array_pop($g);
$d = array_pop($h);

$a = [[1,2,3], [1,2,3]];

$a[0]['5'] = 'asd';
$a[0]['asd'] = 'asd';


if($x){
    $b = 1;
} else {
    $b = "a";
}


$a[0][$b] = 'asd';
