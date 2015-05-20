<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/5/15
 * Time: 5:28 PM
 */

$a = 1;

$z = $a++; //Should be 1
$y = $a--; //Should be 2
$x = ++$a; //Should be 2
$v = --$a; //Should be 1