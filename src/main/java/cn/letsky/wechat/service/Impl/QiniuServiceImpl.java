package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.properties.QiNiuProperties;
import cn.letsky.wechat.service.QiniuService;
import cn.letsky.wechat.util.FileUtils;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * {@link QiniuService}实现类
 */
@Service
public class QiniuServiceImpl implements QiniuService {

    private final Auth auth;
    private final UploadManager uploadManager;
    private final QiNiuProperties qiNiuProperties;

    public QiniuServiceImpl(Auth auth,
                            UploadManager uploadManager,
                            QiNiuProperties qiNiuProperties) {
        this.auth = auth;
        this.uploadManager = uploadManager;
        this.qiNiuProperties = qiNiuProperties;
    }

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
