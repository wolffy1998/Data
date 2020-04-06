package com.mango.shop.spike.controller;

import com.mango.shop.spike.result.CodeMessage;
import com.mango.shop.spike.result.Result;
import com.mango.shop.spike.service.LoginService;
import com.mango.shop.spike.utils.Md5util;
import com.mango.shop.spike.utils.StringUtil;
import com.mango.shop.spike.vo.IsPhone;
import com.mango.shop.spike.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author mango
 * @Since 2020/2/28 20:12
 **/
@Slf4j
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * @return
     * @Valid 对LoginVO递归校验，如果校验失败抛出所有失败的BindException
     */
    @GetMapping("do_login")
    @ResponseBody
    public Result doLogin(@Valid LoginVO loginVO, HttpServletResponse response) throws Exception {
        CodeMessage codeMessage = this.loginService.checkLogin(response, loginVO);
        return new Result(codeMessage);
    }

}
