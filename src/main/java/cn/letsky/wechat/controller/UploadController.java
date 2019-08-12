package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.UploadService;
import cn.letsky.wechat.util.ResultUtils;
import cn.letsky.wechat.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public ResultVO uploads(@RequestParam("file") MultipartFile[] files) {
        List<String> list = Arrays.stream(files)
                .map(file -> uploadImage(file))
                .collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 单次上传图片
     * @param file
     * @return 图片的地址
     */
    private String uploadImage(MultipartFile file) {
        if (file.isEmpty() || file.equals("undefined")) {
            throw new CommonException(ResultEnum.NULL_PICTURE);
        }
        return uploadService.uploadFile(file);
    }
}
