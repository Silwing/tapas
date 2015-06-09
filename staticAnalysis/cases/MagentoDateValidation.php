<?php

function isValidDate($day, $month, $year) {
    $errors = [];
    if(!checkdate($month, $day, $year))
        $errors["invalidDate"] = "The given date is not valid";

    return $errors;
}

function isMinimumDay($day, $month, $year, $required, $minDay) {
    $errors = [];
    if($required && $day == 0 && $month == 0 && $year == 0)
        $errors[] = "The date is required";

    $result = isValidDate($day, $month, $year);
    $errors = array_merge($errors, $result);

    if($minDay > $day)
        $errors[] = "The day should at least be " . $minDay;

    return $errors;
}
$valid = isMinimumDay(27, 5, 2015, true, 6);
$invalid = isMinimumDay(1, 3, 1991, true, 5);



