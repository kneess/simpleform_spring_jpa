package com.perscholas.services;

import com.perscholas.dao.IUserRepo;
import com.perscholas.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    IUserRepo userRepo;

    //userrepo querying db to find user by email
    public User getUserByEmail(String email)
    {
        User foundUser = userRepo.findUserByEmail(email);
        return foundUser;
    }
}
