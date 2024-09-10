package com.hotel.system.config.shiro;

import com.hotel.system.common.constant.CommonConstant;
import com.hotel.system.entity.Permission;
import com.hotel.system.entity.Role;
import com.hotel.system.entity.User;
import com.hotel.system.enums.UserStatusEnum;
import com.hotel.system.service.PermissionService;
import com.hotel.system.service.RoleService;
import com.hotel.system.service.UserService;
import com.hotel.system.util.RegexUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author liuyanzhao
 * @date 2022/04/22
 */
public class MyRealm extends AuthorizingRealm {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;

    @Autowired
    @Lazy
    private PermissionService permissionService;


    /**
     * 认证信息(身份验证) Authentication 是用来验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("认证-->MyShiroRealm.doGetAuthenticationInfo()");
        //1.验证手机号
        User user;
        String account = (String) token.getPrincipal();
        if (RegexUtil.isIdCard(account)) {
            user = userService.findByIdCard(account);
        } else {
            user = userService.findByUserName(account);
        }
        if (user == null) {
            //用户不存在
            log.info("用户不存在! 登录名:{}, 密码:{}", account, token.getCredentials());
            return null;
        }
        Role role = roleService.findByUserId(user.getId());
        if (role != null) {
            user.setRole(role.getRole());
        }


        //2.判断账号是否被封号
        if (!Objects.equals(user.getStatus(), UserStatusEnum.NORMAL.getCode())) {
            throw new LockedAccountException("账号被封禁");
        }

        //3.封装authenticationInfo，准备验证密码
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user, // 手机号
                user.getUserPass(), // 密码
                ByteSource.Util.bytes(CommonConstant.PASSWORD_SALT), // 盐
                getName() // realm name
        );
        System.out.println("realName:" + getName());
        return authenticationInfo;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();

        Role role = roleService.findByUserId(user.getId());

        authorizationInfo.addRole(role.getRole());
        List<Permission> permissions = permissionService.listPermissionsByRoleId(role.getId());
        //把权限的URL全部放到authorizationInfo中去
        Set<String> urls = permissions.stream().map(p -> p.getUrl()).collect(Collectors.toSet());
        authorizationInfo.addStringPermissions(urls);

        return authorizationInfo;
    }
}
