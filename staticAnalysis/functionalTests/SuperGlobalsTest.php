<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/7/15
 * Time: 11:35 AM
 */


function test(){


    var_dump($GLOBALS);
    var_dump($_POST);
    var_dump($_GET);
    var_dump($_SERVER);
    var_dump($_SESSION);
    var_dump($_ENV);
    var_dump($_COOKIE);
    var_dump($_REQUEST);
    var_dump($_FILES);
    $_POST = 1;
}


test();
var_dump($_POST); //Should be 1 or array
