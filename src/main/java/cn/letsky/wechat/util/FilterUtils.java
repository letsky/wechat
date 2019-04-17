package cn.letsky.wechat.util;

import cn.letsky.wechat.constant.ResultEnum;
import cn.letsky.wechat.exception.CommonException;
import cn.letsky.wechat.model.Trie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.TreeMap;

@Component
public class FilterUtils {

    @Autowired
    private Trie trie;

    // 加上PostConnstruct注解防止空指针异常
    @PostConstruct
    public void init() {
        try {
            FileInputStream inputStream = new FileInputStream(getClass().getClassLoader().getResource("keywords").getFile());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.lines().forEach(e -> trie.add(e));
        } catch (FileNotFoundException e) {
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


