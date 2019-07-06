package com.ly.controller;/*
    @author ${user}
    @time 21:37
*/

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName UploadController
 * @Author sky_
 * @Date 2019/6/30
 **/
@Controller
@RequestMapping("/upload")
public class UploadController {
    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @ResponseBody
    @RequestMapping("image")
    public Map<String,Object> uploadImage(@RequestParam("file") MultipartFile m,String name,
                                          HttpServletRequest request){

        Map<String,Object> result = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/");
        String parentPath = realPath+"/upload";
        File parentFile = new File(parentPath);
        if(!parentFile.exists()){
            parentFile.mkdir();
        }
        String originalFilename = m.getOriginalFilename();

        // 新的图片名称
        String fileName = new Date().getTime()+""+new Random().nextInt(100000)
                +originalFilename.substring(originalFilename.lastIndexOf("."));
        File targetFile = new File(parentFile,fileName);
        int state = 0;
        try {
            m.transferTo(targetFile);
        } catch (IOException e) {
            state = 1;
            logger.error(e.getMessage());
        }
        result.put("state",state);
        result.put("fileName",fileName);


        return result;
    }

    @Data
    public static class FileEntity{
        //原始文件名
        private String originalName;
        private String fileName;
        private Long size;
    }

}
