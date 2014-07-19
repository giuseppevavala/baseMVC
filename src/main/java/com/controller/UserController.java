package com.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.SaltedMd5;
import com.domain.POJO.UserPOJO;
import com.domain.service.UserService;

@Controller
public class UserController{
   // private static final int BUFSIZE = 4096;
	private Logger logger  = LoggerFactory.getLogger(this.getClass());;
	
	@Autowired
	UserService userService;
	
	@PostConstruct
	public void init() {
		logger.debug("-------------- >>>>>>>>>>>>>>>>>>>>>> User Controller <<<<<<<<<<<<<<<<<<<< ----------------");
	}

   @RequestMapping(value="/user", method=RequestMethod.POST)
   public @ResponseBody String addUser(
		   UserPOJO userObj) 
   {
	   userObj.setUser(userObj.getUser().toUpperCase().trim());
	   userObj.setEmail(userObj.getEmail().toUpperCase().trim());
	   userObj.setPassword(SaltedMd5.getSecurePassword(userObj.getPassword()));
	   userService.addUser(userObj);
	   
	   logger.info("ADD: " + userObj);
	   return "Registrazione avvenuta con successo";
   }
   
   @RequestMapping(value="/user", method=RequestMethod.GET)
   public @ResponseBody List<UserPOJO> getUser() {
	   logger.info("get request");
	   return userService.getAllUser();
   }
      
  
  
}
