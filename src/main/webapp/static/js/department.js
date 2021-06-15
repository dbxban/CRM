$(function () {

    $('#department_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        singleSelect: true,
        url:'/department/query.do',
        rownumbers:true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'sn',title:'部门编码',width:100},
            {field:'name',title:'部门名称',width:100},
            {field:'state',title:'状态',width:100, formatter: function (value, row, index) {
                    if (value) {
                        return "启用";
                    } else {
                        return "<span style='color: red'>关闭</span>";
                    }
                }}

        ]],
        toolbar: '#department_datagrid_tb',
        pagination: true ,//分页工具栏


        onClickRow: function (index,row) {
            if (row.state) {
                $("#btn_changeState").linkbutton({
                    "text": "关闭",
                    iconCls: 'icon-no'
                });
            } else {
                $("#btn_changeState").linkbutton({
                    "text": "启用",
                    iconCls: 'icon-ok'
                });
            }
        }
    });
    //先初始化dialog在调用它的方法###################
    $("#department_dlg").dialog({
        width: 250,
        height: 200,
        closed:true,//定义是否在初始化的时候关闭面板。
        buttons:'#department_dlg_btn'
    });



    //
    var objectMethod = {
        add: function () {
            //1
            $("#department_dlg").dialog("setTitle", "部门编辑");
            //2.
            $("#department_form").form("clear");
            //3.
            $("#department_dlg").dialog("open");
        },
        save: function () {
            $('#department_form').form('submit', {
                url: "/department/saveOrUpdate.do",
                success: function(data){
                    // console.log(data);//返回的是字符串,
                    var data = $.parseJSON(data);
                    if (data.success) {
                        /* $.messager.alert('温馨提示', '保存成功', 'info');
                         //关闭dialog界面
                         $("#department_dlg").dialog("close");
                         //刷新datagrid
                         $("#department_datagrid").datagrid("reload");*/
                        $.messager.alert("温馨提示", "保存成功", "info", function () {
                            $("#department_dlg").dialog("close");//关闭对话框
                            $("#department_datagrid").datagrid("reload");//刷新当前页
                        })
                    } else {
                        $.messager.alert('温馨提示', '保存失败', 'error');
                    }
                }
            });

        },
        cancel: function () {
            $("#department_dlg").dialog("close");
        },
        edit: function () {
            //1 判断 是否选中需要操作的记录
            var currentRow = $("#department_datagrid").datagrid("getSelected");
            if (!currentRow) {//没有选择记录
                $.messager.alert("温馨提示", "亲,请选择需要操作的部门", "info");
                return;
            }
            //1 设置表头为部门编辑
            $("#department_dlg").dialog("setTitle", "部门编辑")
            //2 打开对话对话框
            $("#department_dlg").dialog("open");
            //3 清空表单里面的数据
            $("#department_form").form("clear")

            $("#department_form").form("load", currentRow);
        },
        changeState: function () {
            //1.判断是否选中需要操作的记录
            var currentRow = $("#department_datagrid").datagrid("getSelected");
            if (!currentRow) {
                $.messager.alert("温馨提示", "请选择需要操作的部门", "info");
                return;
            }
            //2.往后台发送ajax,不需要参数
            $.post("/department/changeState.do", {deptId: currentRow.id}, function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#department_datagrid").datagrid("reload");
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
            $("#department_datagrid").datagrid("load",{
                keyword:keyword
            })
        },
        reload: function () {
            $("#department_datagrid").datagrid("reload");//刷新当前页

        }
    };
    //给所有的a标签绑定点击事件
    $("a[data-cmd]").click(function () {
        var method = $(this).data("cmd");
        objectMethod[method]();
    });


});
