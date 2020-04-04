package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/10 17:43
 **/
@Controller
@RequestMapping(value = "/item/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 根据父节点查询字节点
     * @param pid
     * @return
     */
    @GetMapping(path = "/list")
    @ResponseBody
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid", defaultValue = "0")Long pid) {
            // 参数合法检测
            if (pid == null || pid < 0) {
                // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                return ResponseEntity.badRequest().build();
            }
            List<Category> categories = this.categoryService.queryCategoriesByPid(pid);
            // == null 必须放在前面不然空指针
            if (CollectionUtils.isEmpty(categories)) {
                // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categories);

    }

}
