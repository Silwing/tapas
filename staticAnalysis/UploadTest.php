<?php

if(count($_FILES) > 0) {
    var_dump($_FILES, $_POST);
}

?>
<!DOCTYPE html>
<html>
    <head><title>UploadTest</title></head>
    <body>
        <form method="post" enctype="multipart/form-data">
            Fil: <input type="file" name="testFile"> <input name="submit" type="submit" value="Upload">
        </form>
    </body>
</html>
