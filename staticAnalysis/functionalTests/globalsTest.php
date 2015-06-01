<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/11/15
 * Time: 10:35 AM
 */


function inc_a(){
    global $a;
    $a++;
}


$a = 0;

inc_a();

var_dump($a); //Should be uintnumber