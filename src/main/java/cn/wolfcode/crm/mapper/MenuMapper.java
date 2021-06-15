package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.domain.Menu;
import cn.wolfcode.crm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    List<Menu> selectRootMenus();

    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    int select4Count(QueryObject qo);

    List<Menu> select4List(QueryObject qo);


    List<Menu> selectChildMenus(Long parentId);

    List<Long> selectMenusByEmpId(Long empId);
}