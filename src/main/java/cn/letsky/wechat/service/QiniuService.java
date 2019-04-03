package cn.letsky.wechat.service;

import org.springframework.web.multipart.MultipartFile;

public interface QiniuService {

    /**
     * 上传图片到七牛云
     * @param file
     * @return 图片的链接
     */
    String uploadFile(MultipartFile file);


}
