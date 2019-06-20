package cn.letsky.wechat.service;

import org.springframework.web.multipart.MultipartFile;

public interface QiniuService {

    /**
     * 上传图片到七牛云
     *
     * @param file 需要上传的文件
     * @return 上传后图片的链接
     */
    String uploadFile(MultipartFile file);
}
