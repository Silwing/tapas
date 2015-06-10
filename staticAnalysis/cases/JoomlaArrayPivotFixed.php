<?php

function pivot($source)
{
    $result = array();

    for ($i = 0; $i < count($source); $i++)
    {
        $resultKey = $source[$i];
        $resultValue = $i;

        if (!array_key_exists($resultKey, $resultKey))
        {
            $result[$resultKey] = [$resultValue];
        } else {
            $result[$resultKey][] = $resultValue;
        }

    }

    return $result;
}

$simpleArr = [1,2,3,4,5,6,7,8,1,5,3,7,9,0,4,2,5,8,4,3,8,9];
$result = pivot($simpleArr);


