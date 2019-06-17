package cn.letsky.wechat.model;

import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    private static ThreadLocal<User> holder = new ThreadLocal<>();

    public User get(){
        return holder.get();
    }

    public void set(User user){
        holder.set(user);
    }

    public void clear(){
        holder.remove();
    }
}
