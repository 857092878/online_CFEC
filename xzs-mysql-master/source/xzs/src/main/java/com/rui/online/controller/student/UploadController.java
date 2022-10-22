package com.rui.online.controller.student;

import com.rui.online.base.RestResponse;
import com.rui.online.context.WebContext;
import com.rui.online.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/21 15:22
 * Version1.0.0
 */

@RestController
@RequestMapping("/api/student/upload")
public class UploadController {

    @Value("${file}")
    private String file;
    @Autowired
    private IUserService userService;
    @Autowired
    private WebContext webContext;

    @RequestMapping("/image")
    public RestResponse questionUploadAndReadExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        long attachSize = multipartFile.getSize(); //长度
        String imgName = multipartFile.getOriginalFilename(); //原始名
        String suffixName = imgName.substring(imgName.lastIndexOf("."));  // 后缀名
        String filePath = file; // 上传后的路径
        String fileName = UUID.randomUUID() + suffixName; // 新文件名

        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        userService.changePicture(webContext.getCurrentUser(), fileName);
        try {
            multipartFile.transferTo(dest);
        } catch (IOException e) {
//            e.printStackTrace();
            return RestResponse.fail(2,e.getMessage());
        } finally {
            return RestResponse.ok(filePath);
        }
    }
}
