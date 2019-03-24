package cn.letsky.wechat.converter;

import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.viewobject.CommentVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentConverter {

    /**
     * 将CommentVO转换成Comment
     * @param commentVO
     * @return
     */
    public static Comment VO2Model(CommentVO commentVO){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVO, comment);
        return comment;
    }

    public static CommentVO Model2VO(Comment comment){
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(comment, commentVO);
        return commentVO;
    }

    public static List<CommentVO> Model2VOList(List<Comment> list){
        List<CommentVO> voList = new ArrayList<>();
        for(Comment comment : list){
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            voList.add(commentVO);
        }
        return voList;
    }
}
