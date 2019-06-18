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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UploadController {

    private final QiniuService qiniuService;

    public UploadController(QiniuService qiniuService) {
        this.qiniuService = qiniuService;
    }

    @PostMapping("/upload")
    public ResultVO uploads(@RequestParam("file") MultipartFile[] files){
        List<String> list = Arrays.stream(files)
                .map(file -> uploadImage(file))
                .collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    private String uploadImage(MultipartFile file){
        if (file.isEmpty() || file.equals("undefined")) {
            throw new CommonException(ResultEnum.NULL_PICTURE);
        }
        String url = qiniuService.uploadFile(file);
        return url;
    }
}
