package com.spring.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.spring.springboot.entity.MyFile;
import com.spring.springboot.mapper.MyFileMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;






//获取图片（静态资源）的接口：/glmg/{图片的uuid}
@RestController
@RequestMapping("/file")
public class FileController {

    private String fileUploadPath="E:\\Learning\\学生外出请假平台\\学生外出请假平台\\src\\main\\files\\";//上传文件路径

    @Resource
    private MyFileMapper myFileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //存储到磁盘
        File uploadFile = new File(fileUploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        //文件唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT + type;
        File file1 = new File(fileUploadPath+fileUUid);
        String url;
        url = " https://bouncypussies.mynatapp.cc/glmg/";
//        url = "http://localhost:8080/file/";
        //存储到数据库
        file.transferTo(file1);
        MyFile saveFile = new MyFile();
        saveFile.setName(originalFilename);
        saveFile.setSize(size/1024);
        saveFile.setType(type);
        saveFile.setUrl(fileUUid);
        myFileMapper.insert(saveFile);

//        return url+fileUUid;
        return fileUUid;
    }

    @GetMapping("/{fileUUid}")
    public void download(@PathVariable String fileUUid, HttpServletResponse response) throws IOException {
        //根据文件唯一标识码获取文件
        File uploadFile = new File(fileUploadPath+fileUUid);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileUUid,"UTF-8"));
        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }
    public String uploadTest( MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //存储到磁盘
        File uploadFile = new File(fileUploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        //文件唯一标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT + type;
        File file1 = new File(fileUploadPath+fileUUid);
        String url;
//        url = "http://vupbgn.natappfree.cc/file/";
        url = "http://localhost:8080/file/";
        //存储到数据库
        file.transferTo(file1);
        MyFile saveFile = new MyFile();
        saveFile.setName(originalFilename);
        saveFile.setSize(size/1024);
        saveFile.setType(type);
        saveFile.setUrl(fileUUid);
        myFileMapper.insert(saveFile);

        return fileUUid;
    }
}
