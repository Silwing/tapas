<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 11:11 AM
 */


$a = ["asd" => [1,2,3], "dsa" => [1,2,3]];

$a["asd"]['5'] = 'asd';
$a["asd"]['asd'] = 'asd';
$a["dsa"]['asd'] = 'asd'; //Error since map precision
$a["dsa"]['asd'] = 'asd'; //No error since both arrays has been converted to top-array