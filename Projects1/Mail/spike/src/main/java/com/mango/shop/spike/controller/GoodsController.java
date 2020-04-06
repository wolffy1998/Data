package com.mango.shop.spike.controller;

import com.mango.shop.spike.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author mango
 * @Since 2020/3/3 13:17
 **/
@Controller
@RequestMapping("goods")
public class GoodsController {

    @RequestMapping("to_list")
    public String to_list(LoginVO loginVO, Model model) {
        model.addAttribute("loginVO", loginVO);
        return "goods_list";
    }

}
