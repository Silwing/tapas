<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 11:10 AM
 */


$a = [[1,2,3], [1,2,3]];

$a[0]['5'] = 'asd';
$a[0]['asd'] = 'asd';
$a[0]['asd'] = 'asd'; //No error since both arrays has been converted to map
$a[1]['asd'] = 'asd'; //No error since both arrays has been converted to map

