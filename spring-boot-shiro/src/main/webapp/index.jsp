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
    <script type="text/javascript" src="js/ztree/jquery.ztree.all.js"></script>
    <style>
        body {
            padding-top: 20px;
            padding-bottom: 20px;
        }

        .navbar {
            margin-bottom: 20px;
        }
        .hide{
            display: none;
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
                    <li class="active"  showDivId="userListDiv" grid="userJqGrid"  permissionUrl="user:select"><a href="#">用户管理</a></li>
                    <li showDivId="roleListDiv" grid="roleJqGrid"  permissionUrl="role:select"><a href="#">角色管理</a></li>
                    <li class="dropdown">

                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>

    <div class="row" id="userListDiv" tag="list">
        <table id="userJqGrid"></table>
        <div id="userJqGridPager"></div>
    </div>

    <div class="row hide" id="roleListDiv" tag="list">
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

                    <form class="form-horizontal" id="addPremisionForm" action="${pageContext.request.contextPath}/permission/addPermission">

                        <button type="submit" class="hide" id="addPermisionNameButton"></button>
                        <input type="hidden" id="parentIdAdd" name="parentId" class="hide">

                        <div class="form-group">
                            <label for="parentNameAdd" class="col-sm-2 control-label">父菜单名</label>
                            <div class="col-sm-10">
                                <input type="text"  class="form-control" readonly  id="parentNameAdd" placeholder="父菜单名">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="permissionName" class="col-sm-2 control-label">菜单类型</label>
                            <div class="col-sm-10">
                                <select id="addType" name="type" class="form-control">
                                    <option value="0">目录</option>
                                    <option value="1">功能按钮</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="permissionName" class="col-sm-2 control-label">菜单名</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control" id="permissionName" placeholder="菜单名">
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
                    <button type="button" class="btn btn-primary" onclick="addPermission()">
                        保存
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        角色权限
                    </h4>
                </div>
                <div class="modal-body">
                    <input id="currentRoleId" type="hidden" class="hide" value="">
                    <div class="ztree" id="treeDemo"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" onclick="allocatePermission()">
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
        check: {
            enable: true
        },
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: true
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
            //showRemoveBtn: showRemoveBtn,
            showRenameBtn: showRenameBtn
        },
        callback: {
            beforeEditName: beforeEditName,
            //beforeRemove: beforeRemove,
            //beforeRename: beforeRename,
            //onRemove: onRemove,
            //onRename: onRename
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    function beforeEditName(treeId, treeNode) {
        className = (className === "dark" ? "":"dark");
        showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        setTimeout(function() {
            if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
                setTimeout(function() {
                    zTree.editName(treeNode);
                }, 0);
            }
        }, 0);
        return false;
    };

    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");

        if(!treeNode.isParent){
            return;
        }


        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        if (btn) btn.bind("click", function(){
            var parentMenuName = treeNode.name;
            $("#parentNameAdd").val(parentMenuName);
            $("#parentIdAdd").val(treeNode.id);

           $("#theSecondModal").modal('show');
            return false;
        });
    };
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    };

    function showRenameBtn(treeId, treeNode) {
        return true;
    }

    function addPermission(){
        $("#addPermisionNameButton").trigger('click');
    }

    //分配权限
    function allocatePermission(){
        var roleId = $("#currentRoleId").val();
        var pidList = [];

        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes();

        for(var i=0;i<nodes.length;i++){
            pidList.push(nodes[i].id);
        }
        $.ajax({
            async : true,    //表示请求是否异步处理
            type : "post",    //请求类型
            url : basePath+"/permission/allocatePermission",
            dataType : "json",//返回的数据类型
            data:{
                roleId : roleId,
                pidList:pidList
            },
            success: function (data) {
                $('#myModal').modal('hide');
            },
            error:function (data) {
                alert(data);
            }
        });

    }


    $('#roleJqGrid').off('click', 'a[attr="link"]').on('click', 'a[attr="link"]', function(e) {
        var roleId = $(this).attr("roleId");
        $("#currentRoleId").val(roleId);
        getPermissionTree({id:roleId});
        $('#myModal').modal('show');
    });


    $('#navbar').off('click', 'ul li').on('click', 'ul li', function(e) {
        var showDivId = $(this).attr("showDivId");
        $(this).siblings(".active").removeClass('active');
        $(this).addClass('active');
        $("div[tag='list']").addClass('hide');
        $("#"+showDivId).removeClass('hide');
        var gridId = $(this).attr("grid");
        jQuery("#"+gridId).trigger("reloadGrid");
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
            height:434,/*  autoScroll: false, *//*forceFit: true, */
            colNames:["id","角色名称","备注","创建时间","操作"],
            colModel:[{name:"id",index:"id",width:100,align:"center"},
                {name:"roleName",index:"roleName",width:250,align:"center"},
                {name:"remark",index:"remark",width:250,align:"center"},
                {name:"createTime",index:"createTime",width:250,align:"center"},
                {name:"operation",index:"operation",width:250,align:"center",
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

        $("#userJqGrid").jqGrid({
            height:434,/*  autoScroll: false, *//*forceFit: true, */
            colNames:["id","用户名称","创建时间","操作"],
            colModel:[{name:"id",index:"id",width:100,align:"center"},
                {name:"username",index:"roleName",width:250,align:"center"},
                {name:"createTime",index:"createTime",width:250,align:"center"},
                {name:"operation",index:"operation",width:250,align:"center"
                    }
            ],
            pager:"#userJqGridPager",
            viewrecords:true,
            hidegrid:false,
            url:basePath+"/user/page",
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
            gridComplete: function () {     // 数据加载完成后 添加 采购按钮

            }
            /*prmNames: {
                page: "page",
                rows: "limit",
                order: "order"
            },*/
        });



    });

    $(document).ready(function(){
        checkPermission();
    });

    function getPermissionTree(params) {
        $.ajax({
            async : true,    //表示请求是否异步处理
            type : "post",    //请求类型
            url : basePath+"/permission/getPermissionTree",
            dataType : "json",//返回的数据类型
            data:params,
            success: function (data) {
                $.fn.zTree.init($("#treeDemo"), setting, data);
            },
            error:function (data) {
                alert(data.result);
            }
        });
    };

    function operation_formatter(cellValue,grid, rows, state) {
        var str = '<a attr="link" roleId="'+rows.id+'">编辑权限</a>';
        return str;
    }
    
    function checkPermission() {
        $("[permissionUrl]").each(function(){

            let jqObj = $(this);
            let permissionUrl = jqObj.attr("permissionUrl");

            $.ajax({
                async : true,    //表示请求是否异步处理
                type : "post",    //请求类型
                url : basePath+"/permission/checkPermissionIsExist",
                dataType : "json",//返回的数据类型
                data:{
                    permissionUrl:permissionUrl
                },
                success: function (data) {
                    if(!data){
                        jqObj.addClass('hide');
                    }
                },
                error:function (data) {
                    alert(data.result);
                }
            });
        });
    }




</script>

</html>
