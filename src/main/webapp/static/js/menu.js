$(function () {

    $('#menu_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        singleSelect: true,
        url:'/menu/query.do',
        rownumbers: true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'text',title:'菜单名称',width:100},
            {field:'url',title:'菜单url',width:100},
            {field:'parent',title:'父菜单',hidden:true, width:100 , formatter: function (value, row, index) {
                    if (value) {
                        return value.text; //是对象中的属性
                    } else {
                        return "";
                    }
                }}

        ]],
        toolbar: '#menu_datagrid_tb',
        pagination: true ,  //分页工具栏,
        onDblClickRow: function (index,row) {

            //发送ajax请求
            $.get("/menu/selectChildMenus.do",{parentId:row.id} ,function (data) {
                //重新加载本地数据,加载本地数据，旧的行将被移除。
                $("#menu_datagrid").datagrid("loadData", data);
            });

        }
    });
    //先初始化dialog在调用它的方法###################
    $("#menu_dlg").dialog({
        width: 300,
        height: 250,
        closed:true,//定义是否在初始化的时候关闭面板。
        buttons:'#menu_dlg_btn'
    });

    //
    var objectMethod = {
        add: function () {
            //1
            $("#menu_dlg").dialog("setTitle", "菜单编辑");
            //2.
            $("#menu_form").form("clear");
            //3.
            $("#menu_dlg").dialog("open");
        },
        save: function () {
            $('#menu_form').form('submit', {
                url: "/menu/saveOrUpdate.do",
                success: function(data){
                    // console.log(data);//返回的是字符串,
                    var data = $.parseJSON(data);
                    if (data.success) {
                        $.messager.alert("温馨提示", "保存成功", "info", function () {
                            $("#menu_dlg").dialog("close");//关闭对话框
                            $("#menu_datagrid").datagrid("reload");//刷新当前页
                        })
                    } else {
                        $.messager.alert('温馨提示', '保存失败', 'error');
                    }
                }
            });

        },
        cancel: function () {
            $("#menu_dlg").dialog("close");
        },
        edit: function () {
            //1 判断 是否选中需要操作的记录
            var currentRow = $("#menu_datagrid").datagrid("getSelected");
            if (!currentRow) {//没有选择记录
                $.messager.alert("温馨提示", "亲,请选择需要操作的菜单", "info");
                return;
            }
            //1 设置表头为菜单编辑
            $("#menu_dlg").dialog("setTitle", "菜单编辑")
            //2 打开对话对话框
            $("#menu_dlg").dialog("open");
            //3 清空表单里面的数据
            $("#menu_form").form("clear")
            //对应这种many2one关联关系,可以自定义一个属性
            currentRow["parent.id"] = currentRow.parent.id;//回显的时候,根据同名匹配规则

            $("#menu_form").form("load", currentRow);
        },
        reload: function () {
            $("#menu_datagrid").datagrid("reload");//刷新当前页

        },
        remove: function () {
            //1.判断是否选中需要操作的记录
            var currentRow = $("#menu_datagrid").datagrid("getSelected");
            if (!currentRow) {
                $.messager.alert("温馨提示", "请选择需要操作的权限", "info");
                return;
            }
            //2.往后台发送ajax,不需要参数
            $.post("/menu/delete.do", {menuId: currentRow.id}, function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#menu_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert("温馨提示", data.msg, "error");
                }
            });

        },
        //返回上级菜单
        backMenu: function () {
            //发送ajax请求
            $.get("/menu/selectRootMenus.do", function (data) {
                //重新加载本地数据,加载本地数据，旧的行将被移除。
                $("#menu_datagrid").datagrid("loadData", data);
            });

        }
    };
    //给所有的a标签绑定点击事件
    $("a[data-cmd]").click(function () {
        var method = $(this).data("cmd");
        objectMethod[method]();
    });


});
