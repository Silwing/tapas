<?php 
error_reporting(E_ALL);
$max_int = PHP_INT_MAX; // Max value for integer on a 32-bit system 
$arr = array(); 

$arr[1] = 'foo'; // New generated index will be 2 
$arr[] = 'bar';
$arr[ $max_int ] = 'bar'; // Caution: Next generated index will be -2147483648 due to the integer overflow! 
$arr[0] = 'bar'; // The highest value should be 2147483648 but due to the i-overflow it is -2147483648 so current index 0 is larger. The new generated index therefore is 1! 
$arr[]  = 'failure'; // Warning: Cannot add element to the array as the next element is already occupied.
var_dump($arr);
?>
