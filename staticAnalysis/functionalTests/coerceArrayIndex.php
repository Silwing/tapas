<?php
/**
 * Created by IntelliJ IDEA.
 * User: budde
 * Date: 5/31/15
 * Time: 5:50 PM
 */

$a = ["Hej", "Verden", "C"];
$k = $a[null];
var_dump($k); //Should be null or notuintstring
$a[null] = "Hmm"; //Should give error since null becomes empty string
$a[-1] = "Test"; //Should not give error
$a["-1"] = "Test2"; //Should not give error since string is coerced to int

