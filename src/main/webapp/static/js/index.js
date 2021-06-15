/**
 * 1 给tree添加一个点击事件onClick
 2 在点击事件中的操作:
 ① 判断是否存在url属性,如果存在url属性,可以打开新的tab页
 ② 判断在tabs中是否存在对应的tab页(调用tabs的方法exists)
 ③ 如果存在,调用tabs的select方法 打开对应的tab页
 ④ 如果不存在, 打开一个新的tab页

 */

$(function () {
    $("#index_tree").tree({
        url:'/menu/selectEmployeeMenus.do',
        animate: true,
        lines: true,
        onClick: function(node){
            // alert(node.text);  // 在用户点击的时候提示
            if (node.url) {
                //如果tabs中存在了当前点击的节点的该tab页
                if ($("#indext_tabs").tabs("exists", node.text)) {//存在
                    $("#indext_tabs").tabs("select",node.text);
                    return; //终止该方法
                }
                //不存在则打开新的tab页
                $("#indext_tabs").tabs("add",{
                    title: node.text,
                    closable: true,
                    content: "<iframe src='"+node.url+"' height='100%' width='100%' frameborder='0'></iframe>"
                })
            }
        }

    });

});

