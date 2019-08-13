package cn.letsky.wechat.service;

import cn.letsky.wechat.service.Impl.QiniuServiceImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片服务
 * 具体实现类 {@link QiniuServiceImpl}
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
