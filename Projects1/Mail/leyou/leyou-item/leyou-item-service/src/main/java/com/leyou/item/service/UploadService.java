package com.leyou.item.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author mango
 * @Since 2020/2/13 17:49
 **/
@Slf4j
@Service
public class UploadService {

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif", "image/jpeg", "image/png");

    public String uploadImage(MultipartFile file) {
        // 校验文件类型
        // 根据文件的后缀名校验
        String originalFilename = file.getOriginalFilename();
        // 截取文件后缀名
        String end = StringUtils.substringAfterLast(originalFilename, ".");
        // 根据MIME校验
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            // 使用占位符输出变量
            log.info("文件类型不合法：{}", originalFilename);
            return null;
        }
        try{
            // 校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                log.info("文件内容不合法：{}", originalFilename);
                return null;
            }
            // 最好是对图片重命名防止重名
            originalFilename = alisName(end);
            // 输出到本地硬盘 这里要重新生成一个名字防止重复
            file.transferTo(new File("C:\\Users\\mango\\Desktop\\workspace\\img", originalFilename));

            // 回写URL，一般存放图片都都有专门的服务器使用nginx代理

            return "http://image.leyou.com/" + originalFilename;
        } catch (Exception e) {
            log.info("upload file error：{}", originalFilename);
            return null;
        }

    }

    private String alisName(String end) {
        return RandomStringUtils.random(5, new char[]{'a','b','c','d','e','f', '1', '2', '3'})
                + new Date().getTime() + "." + end;
    }
}
