<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 6:13 PM
 */

$i = 123;
$a = [1,2,3];
$a['test'] = 2;
$a[] = 1;
var_dump($a);
$a['asd'] = 1;
var_dump($i); //$i should now be top int
var_dump($a['test2']); //Should be all possible locations and null