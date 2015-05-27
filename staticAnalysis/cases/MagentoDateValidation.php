<?php
/**
 * Created by IntelliJ IDEA.
 * User: Silwing
 * Date: 27-05-2015
 * Time: 11:44
 * Inspired by Magento: /app/code/Magento/Customer/Model/Metadata/Form/Date.php:28 validateValue($value)
 */

function isValidDate($day, $month, $year) {
    $errors = [];

    if($day == 0 || $month == 0 || $year == 0)
        $errors["emptyDate"] = "One or more parts are empty.";

    if(!checkdate($month, $day, $year))
        $errors["invalidDate"] = "The given date is not valid";

    if(count($errors) > 0)
        return $errors;
    else
        return true;
}

function isMinimumDay($day, $month, $year, $required, $minDay) {
    $errors = [];

    if(!$required && $day == 0 && $month == 0 && $year == 0)
        return true;

    if($required && $day == 0 && $month == 0 && $year == 0)
        $errors[] = "The date is required";

    $result = isValidDate($day, $month, $year);
    if($result !== true)
        $errors = array_merge($errors, $result);

    if($minDay > $day)
        $errors[] = "The day should at least be " . $minDay;

    if(count($errors) > 0)
        return $errors;
    else
        return true;
}

$required = $_GET["required"];
$valid = isMinimumDay(27, 5, 2015, $required, 6);
$invalid = isMinimumDay(1, 3, 1991, $required, 5);
$invalid2 = isMinimumDay(1, 13, 2015, $required, 5);
$invalid3 = isMinimumDay(1, 0, 1991, $required, 0);

