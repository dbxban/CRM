$(function () {

    $('#systemLog_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        singleSelect: true,
        url:'/systemLog/query.do',
        rownumbers:true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'opUser_id',title:'操作者的ID',width:100},
            {field:'opTime',title:'操作时间',width:100},
            {field:'opIp',title:'操作的IP',width:100},
            {field:'function',title:'操作的方法',width:100},
            {field:'params',title:'参数',width:100},
        ]],
        toolbar: '#systemLog_datagrid_tb',
        pagination: true ,//分页工具栏


    });

    //
    var objectMethod = {
        deleteAll: function () {
            //2.往后台发送ajax,不需要参数
            $.post("/systemLog/deleteAll.do", function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#systemLog_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert("温馨提示", data.msg, "error");
                }
            });

        }
    };
    //给所有的a标签绑定点击事件
    $("a[data-cmd]").click(function () {
        var method = $(this).data("cmd");
        objectMethod[method]();
    });


});
