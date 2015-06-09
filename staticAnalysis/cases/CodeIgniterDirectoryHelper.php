<?php

function directory_map($source_dir, $directory_depth)
{
    if ($fp = opendir($source_dir))
    {
        $filedata = [];
        $new_depth	= $directory_depth - 1;
        while (FALSE !== ($file = readdir($fp)))
        {
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

