$(function () {

    $('#role_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        // singleSelect: true,
        url:'/role/query.do',
        rownumbers:true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'sn',title:'角色编码',width:100},
            {field:'name',title:'角色名称',width:100}
        ]],
        toolbar: '#role_datagrid_tb',
        pagination: true //分页工具栏
    });

    //先初始化dialog在调用它的方法###################
    $("#role_dlg").dialog({
        width: 400,
        height: 450,
        closed:true,//定义是否在初始化的时候关闭面板。
        buttons:'#role_dlg_btn',
        onClose: function () {
            $("#role_form").form("clear");
            $("#selectedPermissions").datagrid("loadData", []);
            $("#allPermissions").datagrid("load");
        }
    });

    //先初始化角色编辑框中的两个datagrid
    $("#allPermissions").datagrid({
        width:180,
        height:250,
        url:'/permission/listAll.do', //数据表格远程加载数据
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        columns:[[
            {field:'name',title:'所有权限',width:100}
        ]],

        onClickRow: function (index,row) {
            //1.把当前行数据移动到已选框中
            $("#selectedPermissions").datagrid("appendRow", row);
            //2.把当前行删除
            $("#allPermissions").datagrid("deleteRow", index);
        }
    });

    $("#selectedPermissions").datagrid({
        width:180,
        height:250,
        url: "/permission/getByRoleId.do", //在加载的时候发送这个请求
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        columns:[[
            {field:'name',title:'拥有权限',width:100}
        ]],

        onClickRow: function (index,row) {
            //1.把当前行数据移动到左框中
            $("#allPermissions").datagrid("appendRow", row);
            //2.把当前行删除
            $("#selectedPermissions").datagrid("deleteRow", index);
        },
        onLoadSuccess: function (data) {   //加载已有的权限数据
            //在加载成功之后,把左边相同的行删除
            console.log(data);
            //1.
            var rows = data.rows;
            //2.获取已有权限id集合
            var ids = $.map(rows, function (value) {
                return value.id;
            });
            //遍历所有权限数据,取出id判断
            var allRows = $("#allPermissions").datagrid("getRows");
            for (var i = allRows.length-1; i >= 0; i--) {
                //判断当前id是否在IDS中
                if ($.inArray(allRows[i].id, ids) != -1) {
                    $("#allPermissions").datagrid("deleteRow", i);
                }
            }
        }
    });
    //初始化树形表格下拉框
    $("#menu-combotree").combotree({
        url: '/menu/selectRootMenus.do',
        multiple: true,
        cascadeCheck: false,
        onClick: function (node) {
            var combotree =  $("#menu-combotree").combotree("tree");//返回树形对象。
            //获得上级节点
            var parentNode = combotree.tree("getParent", node.target);
            combotree.tree("check",parentNode.target);//把父节点选中
        }
    });


    //抽取方法,到一个对象中
    var objectMethod = {

        add: function () {
            //1
            $("#role_dlg").dialog("setTitle", "角色编辑");
            //2.
            $("#role_form").form("clear");
            //3.
            $("#role_dlg").dialog("open");
        },
        //保存的时候提交表单
        save: function () {
            $('#role_form').form('submit', {
                url: "/role/saveOrUpdate.do",
                onSubmit: function(param){ //提交额外的参数
                   //获取选择列表中的所有行
                    var rows = $("#selectedPermissions").datagrid("getRows");
                    //
                    for (i = 0; i < rows.length; i++) {
                        param["permissions[" + i + "].id"] = rows[i].id;
                    }
                    //在保存时候处理参数
                    var menuIds = $("#menu-combotree").combotree("getValues");
                    for (i = 0; i < menuIds.length; i++) {
                        param["menus[" + i + "].id"] = menuIds[i];
                    }
                },
                success: function(data){
                    // console.log(data);//返回的是字符串,
                    var data = $.parseJSON(data);
                    if (data.success) {
                        /* $.messager.alert('温馨提示', '保存成功', 'info');
                         //关闭dialog界面
                         $("#role_dlg").dialog("close");
                         //刷新datagrid
                         $("#role_datagrid").datagrid("reload");*/
                        $.messager.alert("温馨提示", "保存成功", "info", function () {
                            $("#role_dlg").dialog("close");//关闭对话框
                            $("#role_datagrid").datagrid("reload");//刷新当前页
                        })
                    } else {
                        $.messager.alert('温馨提示', '保存失败', 'error');
                    }
                }
            });

        },
        cancel: function () {
            $("#role_dlg").dialog("close");
            /*if (!currentRow) {
                alert(1);
                //关闭会话窗口的时候执行的
                $("#role_form").form("clear");
                //给已选的权限列表,加载一个空的数据
                $("#selectedPermissions").datagrid("loadData", []);
                //重载所有的权限数据
                $("#allPermissions").datagrid("load");
            }*/
        },
        edit: function () {
            //1 判断 是否选中需要操作的记录
            currentRow = $("#role_datagrid").datagrid("getSelected");
            if (!currentRow) {//没有选择记录
                $.messager.alert("温馨提示", "亲,请选择需要操作的角色", "info");
                return;
            }
            //1 设置表头为角色编辑
            $("#role_dlg").dialog("setTitle", "角色编辑")
            //2 打开对话对话框
            $("#role_dlg").dialog("open");
            //3 清空表单里面的数据
            $("#role_form").form("clear")
            //查询出对应的菜单,然后设置值,
            $.post("/role/selectMenuIdByRoleId.do", {roleId: currentRow.id}, function (data) {
                $("#menu-combotree").combotree("setValues", data);
            });

            $("#role_form").form("load", currentRow);
            //查询加载,注意要在对应的datagrid中加url
            $("#selectedPermissions").datagrid("load",{roleId:currentRow.id})

        },
        remove: function () {
            //1.判断是否选中需要操作的记录
            var currentRow = $("#role_datagrid").datagrid("getSelected");
            if (!currentRow) {
                $.messager.alert("温馨提示", "请选择需要操作的角色", "info");
                return;
            }
            //2.往后台发送ajax,不需要参数
            $.post("/role/delete.do", {roleId: currentRow.id}, function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#role_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert("温馨提示", data.msg, "error");
                }
            });

        },
        batchRemove: function () {
            //1.判断是否选中需要操作的记录
            var currentRows = $("#role_datagrid").datagrid("getSelections");
            if (!currentRows) {
                $.messager.alert("温馨提示", "请至少选中一条记录", "info");
                return;
            }
            var ids = $.map(currentRows,function (value) {
                return value.id;
            });
            console.log(ids);

            //2.往后台发送ajax,不需要参数
            $.post("/role/batchRemove.do", {roleIds:ids}, function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#role_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert("温馨提示", data.msg, "error");
                }
            });

        },
        searchForm: function () {
            //1.拿到表单的数据
            var keyword = $("input[name='keyword']").val();
            //loda方法
            $("#role_datagrid").datagrid("load",{
                keyword:keyword
            })
        },
        reload: function () {
            $("#role_datagrid").datagrid("reload");//刷新当前页

        }
    };
    //给所有的a标签绑定点击事件
    $("a[data-cmd]").click(function () {
        var method = $(this).data("cmd");
        objectMethod[method]();
    });

});
