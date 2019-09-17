package cn.letsky.wechat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FollowServiceTest {

    private final String FROM_USER = "oNSbI5WcFIhroNVMFxJFqq5vxHNc";
    private final String TO_USER = "a";

    @Autowired
    private FollowService followService;

    @Test
    public void follow() {
        followService.follow(FROM_USER, TO_USER);
    }

    @Test
    public void unFollow() {
        followService.unFollow(FROM_USER, TO_USER);
    }

    @Test
    public void getFollowing() {
        Set<String> following = followService.getFollowing(FROM_USER);
        System.out.println(following);
    }

    @Test
    public void getFollowers() {
        Set<String> followers = followService.getFollowers(FROM_USER);
        System.out.println(followers);
    }

    @Test
    public void getFollowingCount() {
        Long followingCount = followService.getFollowingCount(FROM_USER);
        System.out.println(followingCount);
    }

    @Test
    public void getFollowersCount() {
        Long followersCount = followService.getFollowersCount(FROM_USER);
        System.out.println(followersCount);
    }

    @Test
    public void isFollowing() {
        boolean b = followService.isFollowing(FROM_USER, "a");
        System.out.println(b);
    }

    @Test
    public void isMutualFollowing() {
        boolean mutualFollowing = followService.isMutualFollowing(FROM_USER, "b");
        System.out.println(mutualFollowing);
    }
}