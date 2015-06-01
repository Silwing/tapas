<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 10:52 AM
 */

if($_POST['123']){
    $a = "test";
}


$b = [$a=>1];
var_dump($b); //Should be map
var_dump($b[""]); //Should be 1 or null
var_dump($b["test"]); //Should be 1 or null
var_dump($b[$a]); //Should be 1 or null
var_dump($b["notTest"]); //Should be  null