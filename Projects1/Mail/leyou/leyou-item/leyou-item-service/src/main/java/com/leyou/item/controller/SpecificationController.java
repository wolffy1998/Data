package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/15 13:58
 **/
@Controller
@RequestMapping("item/spec")
public class SpecificationController {

    @Autowired
    SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups = this.specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * RequestMapping及其衍生注解如果只有一个value参数，那么直接写值编译器会帮我们加上value = 属性,这是JDK的新特性
     * RequestParam注解如果默认是必传，如果指定默认值可以不必传
     * 如果没有用RequestParam注解，并且没有传入绑定名称的参数，那么Spring MVC会帮我们传入null，这是基数数据类型的参数会报异常状态错误
     * RequestParam默认就帮我们做了非空验证，所以一般情况下都要使用这个注解
     * 如果参数是个对象那么就不要用这个注解，但是对象的属性不能有基数数据类型防止报IllegalStateException
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "search", required = false) Boolean search) {
        List<SpecParam> specParams = this.specificationService.queryParams(gid, cid, generic, search);

        if (CollectionUtils.isEmpty(specParams)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParams);
    }

}

