<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/10/15
 * Time: 1:39 PM
 */



function a($a){
    return a($a+1);
}

$c = 123;


$b = a(1); //The end node is not reachable, hence the resulting analysis-lattice should be empty. The analysis should however terminate.

$a = 123;
