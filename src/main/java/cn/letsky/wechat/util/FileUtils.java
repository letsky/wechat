package cn.letsky.wechat.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileUtils {

    public static String QINIU_DOMAIN_PREFIX =
            "http://ponxfa1er.bkt.clouddn.com/";
    
    public static String[] IMAGE_FILE_SUFFIX =
            new String[] { "png", "jpg", "bmp", "jpeg" };

    /**
     * 判断是否是图片
     * @param suffix
     * @return
     */
    private static boolean isFileAllowed(String suffix) {
        for (String ext : IMAGE_FILE_SUFFIX) {
            if (ext.equals(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成文件名
     * @param file
     * @return
     */
    public static String fileName(MultipartFile file){
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
