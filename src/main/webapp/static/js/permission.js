$(function () {

    $('#permission_datagrid').datagrid({
        fit:true,
        fitColumns: true,
        singleSelect: true,
        url:'/permission/query.do',
        rownumbers: true,
        columns:[[
            {field:'id',title:'',width:6,hidden:true},
            {field:'',title:'',width:10,checkbox:true},
            {field:'name',title:'权限名称',width:100},
            {field:'resource',title:'权限表达式',width:100}
        ]],
        toolbar: '#permission_datagrid_tb',
        pagination: true //分页工具栏
    });

    //抽取方法,到一个对象中
    var objectMethod = {
        remove: function () {
            //1.判断是否选中需要操作的记录
            var currentRow = $("#permission_datagrid").datagrid("getSelected");
            if (!currentRow) {
                $.messager.alert("温馨提示", "请选择需要操作的权限", "info");
                return;
            }
            //2.往后台发送ajax,不需要参数
            $.post("/permission/delete.do", {permissionId: currentRow.id}, function (data) {
                if (data.success) {
                    $.messager.alert("温馨提示", "操作成功", "info", function () {
                        //重新加载页面
                        $("#permission_datagrid").datagrid("reload");
                    });
                } else {
                    $.messager.alert("温馨提示", data.msg, "error");
                }
            });

        },
        reload: function () {
            //在确定加载后再执行
            $.messager.confirm('提示:', '加载的时间可能有些长,确定要加载吗？', function(yes){
                if (yes){
                   //发送ajax请求,重新加载数据
                    $.post("/permission/reload.do", {}, function (data) {
                        if (data.success) {
                            $.messager.alert('提示:', '加载成功！', 'info');
                            $("#permission_datagrid").datagrid("reload");
                        } else {
                            $.messager.alert('提示:', data.msg, 'error');
                        }

                    });
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
