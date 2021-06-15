package cn.wolfcode.crm.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryObject {
    private int page;//当前页
    private int rows;//页面容量
    private String sort;
    private String order;
//    private Long parentId; //父菜单的id

    public int getStart(){
        return (page - 1) * rows;
    }

}
