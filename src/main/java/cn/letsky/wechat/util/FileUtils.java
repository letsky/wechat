package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    // 图片后缀
    public static String[] IMAGE_FILE_SUFFIX =
            new String[]{"png", "jpg", "bmp", "jpeg"};

    /**
     * 生成文件名
     *
     * @param file 需要上传的文件
     * @return 文件名
     */
    public static String createFileName(MultipartFile file) {
        int doPos = checkOriginalFileName(file.getOriginalFilename());
        //后缀名
        String suffix = file.getOriginalFilename().substring(doPos + 1);
        checkSuffix(suffix);
        StringBuilder builder = new StringBuilder();
        String prefix = UUID.randomUUID().toString()
                .replaceAll("-", "");
        builder.append(prefix)
                .append(".")
                .append(suffix);
        return builder.toString();
    }

    /**
     * 根据后缀名判断是否是图片
     *
     * @param suffix 文件后缀名
     * @throws CommonException
     */
    private static void checkSuffix(String suffix) {
        List<String> ext = Arrays.asList(IMAGE_FILE_SUFFIX);
        if (!ext.contains(suffix))
            throw new CommonException(ResultEnum.NOT_SUPPORT_SUFFIX);
    }

    /**
     * 检查文件名
     *
     * @param fileName
     * @return
     */
    private static int checkOriginalFileName(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new CommonException(ResultEnum.EMPTY_FILENAME);
        }
        int dotPosition = fileName.lastIndexOf(".");
        //排除文件名只有一个点以及没有后缀的情况
        if (dotPosition <= 0) {
            throw new CommonException(ResultEnum.INCORRECT_FILENAME);
        }
        return dotPosition;
    }

}
