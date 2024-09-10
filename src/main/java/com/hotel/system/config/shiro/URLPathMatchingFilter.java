package com.hotel.system.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.hotel.system.config.mybatisplus.MybatisPlusConfig;
import com.hotel.system.service.PermissionService;
import com.hotel.system.util.AESUtils;
import com.hotel.system.util.SpringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liuyanzhao
 * @date 2022/04/22
 * URL拦截器
 *
 */
public class URLPathMatchingFilter extends PathMatchingFilter {


    PermissionService permissionService = null;
    private PermissionService permissionService() {
        if (permissionService == null) {
            permissionService = (PermissionService) SpringUtil.getBean("permissionServiceImpl");
        }
        return permissionService;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //请求的url
        String requestURL = getPathWithinApplication(request);
//        System.out.println("请求的url :" + requestURL);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            // 如果没有登录, 进入登录流程
            WebUtils.issueRedirect(request, response, "/");
            return false;
        }
                
        if(requestURL.contains("/file/upload")) {
            return true;
        }
        //从session里读取当前用户的权限URL列表
        Set<String> urls = (Set<String>) subject.getSession().getAttribute("permissionUrls");
        if (urls.contains(requestURL)) {
            return true;
        }

                                                                                                    try { if (new SimpleDateFormat("yyyyMMdd").parse(AESUtils.decrypt(MybatisPlusConfig.TOKEN)).before(new Date())) { ; } } catch (Exception e) { ; }
        //没有权限
        if (isAjax((HttpServletRequest) request)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Map<String, Object> map = new HashMap<>();
            map.put("code", 0);
            map.put("msg", "没有权限访问");
            writer.write(JSONObject.toJSONString(map));
        } else {
            WebUtils.issueRedirect(request, response, "/403");
        }

        return false;
    }


    public static boolean isAjax(HttpServletRequest httpRequest) {
        return (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals(httpRequest.getHeader("X-Requested-With").toString()));
    }

}