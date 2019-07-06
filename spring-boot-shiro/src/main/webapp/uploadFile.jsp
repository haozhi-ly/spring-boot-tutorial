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
    <script type="text/javascript" src="js/ajaxfileupload.js"></script>


    <!-- We support more than 40 localizations -->
    <script type="text/javascript" src="js/trirand/i18n/grid.locale-en.js"></script>
    <!-- This is the Javascript file of jqGrid -->
    <script type="text/javascript" src="js/trirand/jquery.jqGrid.min.js"></script>
    <!-- A link to a Boostrap  and jqGrid Bootstrap CSS siles-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href="css/trirand/ui.jqgrid-bootstrap.css" />
    <link rel="stylesheet"  href="css/ztree/zTreeStyle.css" />

    <script>
        $.jgrid.defaults.width = 780;
        $.jgrid.defaults.responsive = true;
        $.jgrid.defaults.styleUI = 'Bootstrap';
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

<div class="container">
    <form  method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label>文件</label>
            <input type="file" name="file" id="file">
        </div>

        <button type="button" id="button" class="btn-default btn">提交</button>
    </form>
    
    <div class="row" id="showImg">

    </div>
    
</div>

</body>
<script type="text/javascript">


    $(document).ready(function(){
        $("#button").click(function(){
            ajaxFileUpload();
        });


    });

    function ajaxFileUpload() {
        $.ajaxFileUpload
        (
            {
                url: basePath+"/upload/image", //用于文件上传的服务器端请求地址
                type: 'post',
                data: { "name": "ly" },
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'file', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    if(data.state == 0){
                        alert("上传成功：filename:"+data.fileName);

                        var str = "<dl><dt><img  src='"+basePath+"/upload/"+data.fileName+"'/></dt><dd>"+data.fileName+"</dd></dl>";
                        $("#showImg").append(str);
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }
            }
        )
        return false;
    }





</script>

</html>
