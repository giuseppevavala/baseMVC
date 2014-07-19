package com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.DAO.UserDAO;
import com.domain.POJO.UserPOJO;

@Service("userSvc")
public class UserService {
      
    @Autowired
    private UserDAO userDAO;
 
     
   	public void addUser (UserPOJO user)
   	{
   		userDAO.addObject(user);
   	}

   	public UserPOJO getFromUser(String userName) {		
   		return (UserPOJO) userDAO.getObjWithLikeCondition("user", userName);
   	}
   	
   	public UserPOJO getFromMail(String email) {		
   		return (UserPOJO) userDAO.getObjWithLikeCondition("email", email);
   	}
   	
   	public List<UserPOJO> getAllUser() {		
   		return userDAO.getAll();
   	}
}
