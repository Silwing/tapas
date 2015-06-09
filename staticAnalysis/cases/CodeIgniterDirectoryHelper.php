<?php
/**
 * Created by IntelliJ IDEA.
 * User: Silwing
 * Date: 20-05-2015
 * Time: 10:27
 */

function directory_map($source_dir, $directory_depth = 0)
{
    if ($fp = opendir($source_dir))
    {
        $filedata = [];
        $new_depth	= $directory_depth - 1;
        while (FALSE !== ($file = readdir($fp)))
        {
            //is_dir($source_dir.$file) && $file .= '/';
            if (($directory_depth < 1 || $new_depth > 0) && is_dir($source_dir.$file))
            {
                $filedata[$file] = directory_map($source_dir.$file, $new_depth);
            }
            else
            {
                $filedata[] = $file;
            }
        }
        closedir($fp);
        return $filedata;
    }

    return FALSE;
}

$result = directory_map("testDir", 2);