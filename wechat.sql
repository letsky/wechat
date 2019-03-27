create table if not exists `user` (
    `openid` varchar(64) not null comment '用户微信id',
    `session_key` varchar(64) comment '',
    `nickname` varchar(64) not null comment '用户昵称',
    `gender` tinyint(1) not null comment '用户性别',
    `avatar_url` varchar(256) not null comment '头像链接',
    `email` varchar(128) default null comment '邮箱',
    `password` varchar(128) default null comment '用户密码',
    `salt` varchar(32) default null, 
    `created` timestamp NOT NULL default current_timestamp comment '注册时间',
    primary key(`openid`)
)ENGINE=InnoDB default charset=utf8mb4 comment='用户表';

create table if not exists `article`(
    `id` int(10) not null auto_increment comment '文章主键',
    `content` varchar(512) not null comment '文本内容',
    `img` varchar(1024) default null comment '图片',
    `created` timestamp not null comment '发表时间',
    `openid` varchar(64) not null comment '发表人的openid',
    `status` tinyint(1) not null default '0' comment '0为正常，1为删除',
    `tag` varchar(200) default null comment '话题',
    `comment_num` int(10) unsigned DEFAULT '0' comment '评论数',
    `like_num` int(10) unsigned DEFAULT '0' comment '点赞数',
    `allow_comment` tinyint(1) DEFAULT '0' comment '是否允许评论，0为允许，1为不允许',
    primary key(`id`)
)ENGINE=InnoDB default charset=utf8mb4 comment='内容表';

create table if not exists `comment`(
    `id` int(10) not null auto_increment comment '评论主键id',
    `uid` varchar(64) not null comment '用户微信id',
    `entity_id` int(10) not null comment '被评论的资源id',
    `entity_type` tinyint(1) not null comment '评论类型',
    `content` varchar(512) not null comment '评论内容',
    `created` timestamp not null default current_timestamp comment '发表时间',
    primary key(`id`)
)ENGINE=InnoDB default charset=utf8mb4 comment='评论表';

