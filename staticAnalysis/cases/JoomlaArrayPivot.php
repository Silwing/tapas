<?php

function pivot($source)
{
    $result = array();
    $counter = array();

    for ($i = 0; $i < count($source); $i++)
    {
        $resultKey = $source[$i];
        $resultValue = $i;

        if (array_key_exists($resultKey, $counter))
        {
            $result[$resultKey] = $resultValue;
            $counter[$resultKey] = 1;
        }
        else if ($counter[$resultKey] == 1)
        {
            $result[$resultKey] = [$result[$resultKey], $resultValue];
            $counter[$resultKey] = $counter[$resultKey]+1;
        }
        else
        {
            $result[$resultKey][] = $resultValue;
        }

    }

    return $result;
}

$simpleArr = [1,2,3,4,5,6,7,8,1,5,3,7,9,0,4,2,5,8,4,3,8,9];
$result = pivot($simpleArr);


