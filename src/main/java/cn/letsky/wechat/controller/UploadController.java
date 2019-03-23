package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.service.QiniuService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.viewobject.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private QiniuService qiniuService;

    @PostMapping("/upload")
    public ResultVO<String> upload(@RequestParam("file") MultipartFile file) {
        String url = qiniuService.uploadFile(file);
        if (url != null)
            return ResultUtils.success(url);
        return ResultUtils.error(ResultEnum.FAIL);
    }
}
