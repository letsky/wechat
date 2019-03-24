package cn.letsky.wechat.service.Impl;

import cn.letsky.wechat.converter.CommentConverter;
import cn.letsky.wechat.dao.CommentDao;
import cn.letsky.wechat.dao.UserDao;
import cn.letsky.wechat.model.Comment;
import cn.letsky.wechat.model.User;
import cn.letsky.wechat.service.CommentService;
import cn.letsky.wechat.viewobject.CommentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Override
    public CommentVO save(CommentVO commentVO) {
        Comment comment = commentDao.save(CommentConverter.VO2Model(commentVO));
        return CommentConverter.Model2VO(comment);
    }

    @Override
    public List<CommentVO> findAllByOwnerId(Integer ownerId) {
        List<Comment> commentList = commentDao.findAllByOwnerId(ownerId);
        List<CommentVO> commentVOList = CommentConverter.Model2VOList(commentList)
                .stream().map(vo -> {
                    User fromUser = userDao.getOne(vo.getFromId());
                    if (fromUser != null){
                        vo.setFromName(fromUser.getNickname());
                        vo.setFromAvatar(fromUser.getAvatarUrl());
                    }
                    String toId = vo.getToId();
                    if (!StringUtils.isBlank(toId)){
                        User toUser = userDao.getOne(toId);
                        if (toUser != null){
                            vo.setFromName(toUser.getNickname());
                            vo.setFromAvatar(toUser.getAvatarUrl());
                        }
                    }
                    return vo;
                }).collect(Collectors.toList());
        return sortData(commentVOList);
    }

    private List<CommentVO> sortData(List<CommentVO> vos){
        List<CommentVO> list = new ArrayList<>();
        for (int i = 0; i < vos.size(); i++){
            CommentVO vo1 = vos.get(i);
            List<CommentVO> children = new ArrayList<>();
            for (int j = 0; j < vos.size(); j++){
                CommentVO vo2 = vos.get(j);
                if (vo2.getPid() == null){
                    continue;
                }
                if (vo1.getPid().equals(vo2.getPid())){
                    children.add(vo2);
                }
            }
            if (vo1.getPid() == null){
                list.add(vo1);
            }
        }
        return list;
    }
}
