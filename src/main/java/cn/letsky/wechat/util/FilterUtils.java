package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.domain.model.Trie;
import cn.letsky.wechat.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Component
public class FilterUtils {

    @Autowired
    private Trie trie;

    // 加上PostConnstruct注解防止空指针异常
    @PostConstruct
    public void init() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/keywords");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            bufferedReader.lines().forEach(e -> {
                if (e.length() > 1)
                    trie.add(e);
            });
        } catch (NullPointerException | UnsupportedEncodingException e) {
            throw new CommonException(ResultEnum.NULL_WORD_FILE);
        }
    }

    public boolean isSensitive(String text) {
        boolean flag = false;
        Trie.Node node = trie.getRoot();
        //回滚数
        int begin = 0;
        //当前位置
        int position = 0;
        while (position < text.length()) {
            char c = text.charAt(position);
            node = node.next.get(c);
            if (node == null) {
                //开头不存在敏感词
                position = begin + 1;
                begin = position;
                node = trie.getRoot();
            } else if (node.isWord) {
                flag = true;
            } else {
                ++position;
            }
        }
        return flag;
    }
}


