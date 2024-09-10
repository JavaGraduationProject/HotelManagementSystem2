package com.hotel.system.controller.common;

import com.hotel.system.enums.CommonParamsEnum;
import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误页面控制器
 * @author liuyanzhao
 * @date 2022/04/22
 */
@Controller
public class CommonController implements ErrorController {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonController.class);

    /**
     * 渲染404，500
     *
     * @param request request
     * @return String
     */
    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode.equals(CommonParamsEnum.NOT_FOUND.getValue())) {
            return "redirect:/404";
        } else {
            return "redirect:/500";
        }
    }

    /**
     * 渲染403页面
     *
     * @return String
     */
    @GetMapping(value = "/403")
    public String fourZeroThree() {
        return "common/error/403";
    }


    /**
     * 渲染404页面
     *
     * @return String
     */
    @GetMapping(value = "/404")
    public String fourZeroFour() {
        return "common/error/404";
    }


    /**
     * 渲染500页面
     *
     * @return String
     */
    @GetMapping(value = "/500")
    public String fiveZeroZero() {
        return "common/error/500";
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
