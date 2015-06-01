<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 6/1/15
 * Time: 9:08 AM
 */


function &a(&$b){
    return 1+1;
}

var_dump(a($a)); //Should be 2
