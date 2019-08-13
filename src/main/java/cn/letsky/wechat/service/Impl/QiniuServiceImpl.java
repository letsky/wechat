package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.configure.properties.QiNiuProperties;
import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.service.UploadService;
import cn.letsky.wechat.util.FileUtils;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 使用七牛云服务上传图片
 * {@link UploadService}实现类
 */
@Service
public class QiniuServiceImpl implements UploadService {

    private final String getToken;
    private final UploadManager uploadManager;
    private final QiNiuProperties qiNiuProperties;

    public QiniuServiceImpl(String getToken, UploadManager uploadManager,
                            QiNiuProperties qiNiuProperties) {
        this.getToken = getToken;
        this.uploadManager = uploadManager;
        this.qiNiuProperties = qiNiuProperties;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String upToken = getToken;
            String fileName = FileUtils.createFileName(file);
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
