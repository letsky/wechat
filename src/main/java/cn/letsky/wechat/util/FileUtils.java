package cn.letsky.wechat.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    // 图片后缀
    public static String[] IMAGE_FILE_SUFFIX =
            new String[]{"png", "jpg", "bmp", "jpeg"};

    /**
     * 判断是否是图片
     *
     * @param suffix 文件后缀名
     * @return 是图片返回<code>true</code>，不是图片返回<code>false</code>
     */
    private static boolean isFileAllowed(String suffix) {
        List<String> ext = Arrays.asList(IMAGE_FILE_SUFFIX);
        if (ext.contains(suffix))
            return true;
        return false;
    }

    /**
     * 生成文件名
     *
     * @param file 需要上传的文件
     * @return 文件名
     */
    public static String fileName(MultipartFile file) {
        int doPos = file.getOriginalFilename().lastIndexOf(".");
        if (doPos < 0)
            return null;
        //后缀名
        String suffix = file.getOriginalFilename().substring(doPos + 1);
        if (!isFileAllowed(suffix)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        String prefix = UUID.randomUUID().toString()
                .replaceAll("-", "");
        builder.append(prefix)
                .append(".")
                .append(suffix);
        return builder.toString();
    }

}
