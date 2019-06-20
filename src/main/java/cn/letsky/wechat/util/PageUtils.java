package cn.letsky.wechat.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtils {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 5;

    /**
     * 获取分页对象，并校验参数
     *
     * @param page 页数
     * @param size 每页大小
     * @return 分页对象<code>Pageable</code>
     */
    public static Pageable getPageable(Integer page, Integer size) {
        if (page < 1 && size > 20) {
            page = DEFAULT_PAGE;
            size = DEFAULT_SIZE;
        }
        return PageRequest.of(page - 1, size);
    }
}
