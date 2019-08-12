package cn.letsky.wechat.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片服务
 */
public interface UploadService {

    /**
     * 上传图片
     *
     * @param file 图像
     * @return 图像的地址
     */
    String uploadFile(MultipartFile file);
}
