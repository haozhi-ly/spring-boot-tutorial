<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>shiro</title>
<%--
    <link rel="stylesheet" href="css/bootstrap.min.css" />
--%>
<%--
    <link rel="stylesheet" href="css/navbar.css">
--%>
    <script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<%--
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
--%>


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
    <script type="text/javascript" src="js/ztree/jquery.ztree.core.js"></script>
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
    <!-- Static navbar -->
    <nav class="navbar navbar-default row">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">shiro</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">用户管理</a></li>
                    <li><a href="#">角色管理</a></li>
                    <li class="dropdown">

                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>

    <div class="row">
        <table id="roleJqGrid"></table>
        <div id="jqGridPager"></div>
    </div>


   <%-- <div class="modal hide fade" id="myModal">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3>对话框标题</h3>
        </div>
        <div class="modal-body">
            <div class="ztree" id="treeDemo"></div>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn">关闭</a>
            <a href="#" class="btn btn-primary">Save changes</a>
        </div>
    </div>--%>


    <div class="modal fade" id="theSecondModal" tabindex="-1" style="z-index: 2000" role="dialog" aria-labelledby="theSecondModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="theSecondModalLabel">
                        添加权限
                    </h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" id="addPremisionForm" >

                        <div class="form-group">
                            <label for="parentName" class="col-sm-2 control-label">父菜单名</label>
                            <div class="col-sm-10">
                                <input type="text"  class="form-control" id="parentName" placeholder="父菜单名">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="permissionName" class="col-sm-2 control-label">权限名</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control" id="permissionName" placeholder="权限名">
                            </div>
                        </div>


                        <div class="form-group">
                            <label for="addUrl" class="col-sm-2 control-label">权限URL</label>
                            <div class="col-sm-10">
                                <input type="text" name="url" class="form-control" id="addUrl" placeholder="url">
                            </div>
                        </div>


                    </form>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" onclick="addPermission">
                        保存
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
        开始演示模态框
    </button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        模态框（Modal）标题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="ztree" id="treeDemo"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#theSecondModal">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


</div>


</body>
<script type="text/javascript">

    var setting = {
        view: {
            selectedMulti: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {

        }
    };



    $('#roleJqGrid').on('click', 'a[attr="link"]', function(e) {
        $('#myModal').modal('show');
    });

   /*

    $('#myModal').on('show.bs.modal', function () {
        alert("显示模态框");
    });

    $('#myModal').on('hide.bs.modal', function () {
        alert("隐藏模态框");
    });*/


    $(function () {
        var a = "test";
        $("#roleJqGrid").jqGrid({
            height:434,autowidth:true, shrinkToFit:true,/*  autoScroll: false, *//*forceFit: true, */
            colNames:["id","角色名称","备注","创建时间","操作"],
            colModel:[{name:"id",index:"id",autowidth:true,align:"center"},
                {name:"roleName",index:"roleName",autowidth:true,align:"center"},
                {name:"remark",index:"remark",autowidth:true,align:"center"},
                {name:"createTime",index:"createTime",autowidth:true,align:"center"},
                {name:"operation",index:"operation",autowidth:true,align:"center",
                    formatter: operation_formatter }
            ],
            pager:"#jqGridPager",
            viewrecords:true,
            hidegrid:false,
            url:basePath+"/role/page",
            datatype:'json',
            rownumbers: true,
            rowNum : 10,
            rowList : [ 10, 15,30 ],
            jsonReader: {
                root: "dataList",
                page: "currPage",
                total: "totalPages",          //   很重要 定义了 后台分页参数的名字。
                records: "totalCount"
            },
            gridComplete: function () {         // 数据加载完成后 添加 采购按钮

            }
            /*prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },*/
        });


    });

    $(document).ready(function(){
        getPermissionTree();
    });

    function getPermissionTree() {
        $.ajax({
            async : true,    //表示请求是否异步处理
            type : "post",    //请求类型
            url : basePath+"/permission/getPermissionTree",
            dataType : "json",//返回的数据类型
            success: function (data) {
                $.fn.zTree.init($("#treeDemo"), setting, data);
            },
            error:function (data) {
                alert(data.result);
            }
        });
    };

    function operation_formatter(cellValue,grid, rows, state) {
        var str = '<a attr="link">编辑权限</a>';
        return str;
    }




</script>

</html>
