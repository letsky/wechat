package cn.letsky.wechat.controller;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.UploadService;
import cn.letsky.wechat.util.ResultUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * 上传图片的接口
     * Content-Type应该为multipart/form-data
     *
     * @param files 多个图片
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploads(@RequestParam("file") MultipartFile[] files) {
        List<String> list = Arrays.stream(files)
                .map(this::uploadImage)
                .collect(Collectors.toList());
        Map<String, List<String>> map = new HashMap<>(1);
        map.put("images", list);
        return ResultUtils.ok(map);
    }

    /**
     * 单次上传图片
     * @param file 图片
     * @return 图片的地址
     */
    private String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CommonException(ResultEnum.EMPTY_PICTURE);
        }
        return uploadService.uploadFile(file);
    }
}
