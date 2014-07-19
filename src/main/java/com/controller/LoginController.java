package com.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.business.SaltedMd5;
import com.domain.POJO.UserPOJO;
import com.domain.service.UserService;

@Controller
public class LoginController{
   // private static final int BUFSIZE = 4096;
	private Logger logger  = LoggerFactory.getLogger(this.getClass());;
	
	@Autowired
	UserService userService;
	
	@PostConstruct
	public void init() {
		logger.debug("-------------- >>>>>>>>>>>>>>>>>>>>>> Login Controller <<<<<<<<<<<<<<<<<<<< ----------------");
	}

   @RequestMapping(value="/login", method=RequestMethod.POST)
   public @ResponseBody String login(
		   UserPOJO userObj,
		   @RequestParam(value= "remember_me") String rememberMe ) 
   {
	   userObj.setUser(userObj.getUser().toUpperCase().trim());
	   userObj.setEmail(userObj.getEmail().toUpperCase().trim());
	   
	   userObj.setPassword(SaltedMd5.getSecurePassword(userObj.getPassword()));
	   logger.info(userObj.toString());
	   
	   UserPOJO user = userService.getFromMail(userObj.getEmail());
	   if (user == null)
		   user = userService.getFromUser(userObj.getUser());
	   
	   if (user == null)
		   return "Login Error: non esiste user/mail";
	   
	   if (user.getPassword().equalsIgnoreCase(userObj.getPassword()))
		   return "Login";
	   else
		   return "Login Error: Password sbagliata";
   }
  
}
