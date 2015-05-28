<?php
/**
 * Created by IntelliJ IDEA.
 * User: Randi
 * Date: 26-05-2015
 * Time: 16:37
 */

function pivot($source)
{
    $result = array();
    $counter = array();

    for ($i = 0; $i < count($source); $i++)
    {
        $resultKey = $source[$i];
        $resultValue = $i;

        // The counter tracks how many times a key has been used.
        if (empty($counter[$resultKey]))
        {
            // The first time around we just assign the value to the key.
            $result[$resultKey] = $resultValue;
            $counter[$resultKey] = 1;
        }
        else if ($counter[$resultKey] == 1)
        {
            // If there is a second time, we convert the value into an array.
            $result[$resultKey] = array(
                $result[$resultKey],
                $resultValue,
            );
            $counter[$resultKey]++;
        }
        else
        {
            // After the second time, no need to track any more. Just append to the existing array.
            $result[$resultKey][] = $resultValue;
        }
    }

    return $result;
}

$simpleArr = [1,2,3,4,5,6,7,8,1,5,3,7,9,0,4,2,5,8,4,3,8,9];

$result = pivot($simpleArr);

//var_dump($result);