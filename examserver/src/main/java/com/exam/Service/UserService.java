package com.exam.Service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //create user
    public User createUser(User user, Set<UserRole> userRoles);

    //get user by username
    public User getUser(String username);

    //delete user by id
    public void deleteUser(Long userId);

    //update user by id
    public User updateUser(User user);


}
