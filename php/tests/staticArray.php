<?php

function test($name, $value) {
	static $arr = [];
	$arr[$name] = $value;
	var_dump($arr);
}

test("hello", "world");
test(2, 42);
test("we", "rock");
