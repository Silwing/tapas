<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 7:37 PM
 */



function a(&$a) {
    var_dump($a[] = 1); // Should return 1 and yield warning
}


$a = ["test"];
var_dump($a);
a($a);
var_dump($a); //Array should be updated