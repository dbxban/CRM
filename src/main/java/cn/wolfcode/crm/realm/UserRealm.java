package cn.wolfcode.crm.realm;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.mapper.RoleMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRealm extends AuthorizingRealm  { //###注意:这个要交给Spring管理

    //注入mapper代理对象?
    @Autowired
    private EmployeeMapper employeeMapper;
     @Autowired
    private RoleMapper roleMapper;
     @Autowired
    private PermissionMapper permissionMapper;



    @Override
    public String getName() {
        return "UserRealm";
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal();//获取前端传入的用户名
        //去数据库查
        Employee employee = employeeMapper.getByUsername(username);
        if (employee == null) { //没有改用户是返回null,登录失败
            return null;
        }
        return new SimpleAuthenticationInfo(employee,employee.getPassword(),getName());
    }

    //授权方法 ########写好后要在shiro.xml中配置
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //先创建给简单的认证对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        //在这个要对超管做处理
        if (employee.isAdmin()) {
            info.addRole("ADMIN");          //是超管
            info.addStringPermission("*:*"); //所以权限
            return info ;
        }
        //通过员工的id去查询对应的角色
        List<Role> roleList = roleMapper.selectRoleByEmpId(employee.getId());
        Set<String> roleSet = new HashSet<>(); //装角色编码
        for (Role role : roleList) {
            roleSet.add(role.getSn());
        }
        info.setRoles(roleSet);
        //通过员工的id去查询对应的权限
        List<Permission> permissionList = permissionMapper.selectPermissionByEmpId(employee.getId());
        Set<String> permissionSet = new HashSet<>(); //装权限表达式
        for (Permission permission : permissionList) {
            permissionSet.add(permission.getResource()); //
        }
        info.addStringPermissions(permissionSet);

        return info;
    }

}
