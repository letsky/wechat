package cn.letsky.wechat.form;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleForm implements Form {

    private String content;

    private String openid;

    private String tag;

    public ArticleForm() {
    }

    public ArticleForm(String content, String openid, String tag) {
        this.content = content;
        this.openid = openid;
        this.tag = tag;
    }
}
