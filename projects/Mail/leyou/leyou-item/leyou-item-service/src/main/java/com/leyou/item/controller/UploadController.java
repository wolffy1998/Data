package com.leyou.item.controller;

import com.leyou.item.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author mango
 * @Since 2020/2/13 17:03
 **/
@Controller
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;
    private ResponseEntity<String> body;

    /**
     * 使用ResponseEntity返回可以不用ResonseBody注解
     * 传入集合，MultipartFile等必须使用RequestParam指定传入属性名称
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = this.uploadService.uploadImage(file);
        if (StringUtils.isBlank(url)) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(url);

    }
}
