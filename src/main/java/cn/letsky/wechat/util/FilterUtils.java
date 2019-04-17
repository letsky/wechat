package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.TreeMap;

@Component
public class FilterUtils {

    @Autowired
    private Trie trie;

    // 加上PostConnstruct注解防止空指针异常
    @PostConstruct
    public void init() {
        try {
            Resource resource = new ClassPathResource("keywords");
            FileInputStream inputStream = new FileInputStream(resource.getFile());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().forEach(e -> trie.add(e));
        } catch (IOException e) {
            throw new CommonException(ResultEnum.NULL_WORD_FILE);
        }
    }

    public boolean isSensitive(String text){
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


