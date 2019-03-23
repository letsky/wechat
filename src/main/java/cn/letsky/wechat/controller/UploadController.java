package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.QiniuService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UploadController {

    @Autowired
    private QiniuService qiniuService;

    @PostMapping("/upload")
    public ResultVO uploads(@RequestParam("file") MultipartFile[] files){
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files){
            String url = uploadImage(file);
            if (url != null)
                list.add(url);
            else
                throw new CommonException(ResultEnum.FAIL);
        }
        return ResultUtils.success(list);
    }

    private String uploadImage(MultipartFile file){
        if (file.isEmpty()) {
            throw new CommonException(ResultEnum.NULL_PICTURE);
        }
        String url = qiniuService.uploadFile(file);
        return url;
    }
}
