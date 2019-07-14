<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>shiro</title>

    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>

    <!-- We support more than 40 localizations -->
    <script type="text/javascript" src="thirdPlugin/dropzone/dropzone.js"></script>
    <!-- This is the Javascript file of jqGrid -->
    <script type="text/javascript" src="js/trirand/jquery.jqGrid.min.js"></script>
    <!-- A link to a Boostrap  and jqGrid Bootstrap CSS siles-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href="css/trirand/ui.jqgrid-bootstrap.css" />
    <link rel="stylesheet"  href="thirdPlugin/dropzone/dropzone.css" />

    <script>
        // Prevent Dropzone from auto discovering this element:
        Dropzone.options.myAwesomeDropzone = false;
        // This is useful when you want to create the
        // Dropzone programmatically later

        // Disable auto discover for all elements:
        Dropzone.autoDiscover = false;
    </script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body {
            padding-top: 20px;
            padding-bottom: 20px;
        }

        .navbar {
            margin-bottom: 20px;
        }
    </style>

    <script type="text/javascript">
        var basePath = "${pageContext.request.contextPath}";
    </script>
</head>
<body>
<div class="dropzone"
      id="myDropzone"></div>


</body>
<script type="text/javascript">


    $(document).ready(function(){
        $("div#myDropzone").dropzone({
            url: basePath+"/upload/file",//上传文件的地址，
            maxFiles: 10,//最多上传几个图片
            maxFilesize: 5,//图片的大小，单位是M
            addRemoveLinks:true,//是否有删除图片的功能
            dictRemoveFile:"",//删除图片的文字
            acceptedFiles: ".jpg,.jpeg,.png,.gif",//支持的格式
            paramName:'file',//上传的FILE名称，即服务端可以通过此来获取上传的图片，如$_FILES['dropimage']
            init : function() { // dropzone初始化时调用您可以在此处添加事件侦听器
                var myDropzone = this;
                this.on("addedfile",function(file) {
                    var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>移除</button>");
                    removeButton.addEventListener("click",function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        myDropzone.removeFile(file);
                    });
                    file.previewElement.appendChild(removeButton);
                });
                this.on("success", function(file,data) {
                    file["name"]=data.fileName;
                    file["newFileName"]=data.fileName;
                    console.info(myDropzone.getAcceptedFiles());
                });
            }
        });

    });



</script>



<script>
    var o = {
        n:0,
        get a(){
            return this.n;
        },
        set a(value){
            this.n = value;
        }
    };


    o.a = 1;
    console.info(o.a);
    console.info(o.n);

</script>


</html>
