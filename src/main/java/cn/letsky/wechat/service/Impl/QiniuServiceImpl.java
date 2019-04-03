package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.config.QiNiuProperties;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.QiniuService;
import cn.letsky.wechat.util.FileUtils;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    private Auth auth;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private QiNiuProperties qiNiuProperties;


    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String upToken = auth.uploadToken(qiNiuProperties.getBucket());
            String fileName = FileUtils.fileName(file);
            if (fileName == null) {
                throw new CommonException(ResultEnum.NOT_PICTURE);
            }
            Response response = uploadManager.put(file.getBytes(),
                    fileName, upToken);
            if (response.isOK() && response.isJson()){
                StringBuilder builder = new StringBuilder();
                builder.append(qiNiuProperties.getDomain())
                        .append(new Gson().fromJson(response.bodyString(),
                                DefaultPutRet.class).key);
                return builder.toString();
            }
            return null;
        } catch (IOException e) {
            throw new CommonException(ResultEnum.UPLOAD_ERROR);
        }
    }
}
