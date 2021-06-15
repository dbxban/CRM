package cn.wolfcode.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter
public class Role {
    private Long id;

    private String sn;

    private String name;

    //many2many ,一个角色可以有多个权限
    private List<Permission> permissions = new ArrayList<>();
    //many2many
    private List<Menu> menus = new ArrayList<>();

}