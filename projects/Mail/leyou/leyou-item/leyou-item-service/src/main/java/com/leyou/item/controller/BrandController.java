package com.leyou.item.controller;

import com.leyou.common.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/11 16:04
 **/
@Slf4j
@RestController
@RequestMapping("/item/brand")
public class BrandController {
    
    @Autowired
    BrandService brandService;

    @GetMapping("/page")
    @ResponseBody
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(@RequestParam(value = "key", required = false)String key,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "rows", defaultValue = "5")Integer rows,
                                                   @RequestParam(value = "sortBy", required = false)String sortBy,
                                                   @RequestParam(value = "desc", required = false)Boolean desc) {
        System.out.println("请求进来了");
        PageResult<Brand> brandPageResult = brandService.queryBrandsByPage(key, page, rows, sortBy, desc);
        if (brandPageResult == null || CollectionUtils.isEmpty(brandPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    /**
     * 接收JSON数据时只能用一个对象接收
     * 可以定义DTO数据模型接收，或者使用qs把json转为表单数据提交
     * `npm install qs --save`  data: this.$qs.stringify(params)
     * @RequestParam可以接收集合并且可以指定默认值和是否必传
     * @param brand
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addBrand(Brand brand, @RequestParam("cids") List<Long> cids) {

        log.info("请求进来了");

        this.brandService.addBrand(brand, cids);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable("cid") Long cid) {
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        // 判断集合是否为null，或者是否有元素
        if (CollectionUtils.isEmpty(brands)) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

}
