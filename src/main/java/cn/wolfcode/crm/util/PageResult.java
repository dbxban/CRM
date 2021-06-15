package cn.wolfcode.crm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    private int total; //总条数
    private List<?> rows ; //分页查询出的对象集合
}
