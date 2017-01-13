package main.java.com.novencia.dashboard.idea.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import main.java.com.novencia.dashboard.idea.model.User;

@Service("userService")
public class UserServiceImpl implements UserService{
     
    private static final AtomicLong counter = new AtomicLong();
     
    private static List<User> users;
     
    static{
        users= populateDummyUsers();
    }
 
    public List<User> findAllUsers() {
        return users;
    }
     
    public User findById(long id) {
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        user.setId(counter.incrementAndGet());
        users.add(user);
    }
 
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }
 
    public void deleteUserById(long id) {
         
        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
            }
        }
    }
 
    public boolean isUserExist(User user) {
        return findById(user.getId())!=null;
    }
     
    public void deleteAllUsers(){
        users.clear();
    }
 
    public static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam","Name"));
        users.add(new User(counter.incrementAndGet(),"Tom","Name"));
        users.add(new User(counter.incrementAndGet(),"Jerome","Name"));
        users.add(new User(counter.incrementAndGet(),"Silvia","Name"));
        return users;
    }
}