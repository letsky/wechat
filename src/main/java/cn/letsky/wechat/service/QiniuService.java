package cn.letsky.wechat.service;

import org.springframework.web.multipart.MultipartFile;

public interface QiniuService {

    String uploadFile(MultipartFile file);


}
