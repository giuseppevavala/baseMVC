package com.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BaseController{
   // private static final int BUFSIZE = 4096;
	private Logger logger  = LoggerFactory.getLogger(this.getClass());;
	
	
	@PostConstruct
	public void init() {
		logger.debug("-------------- >>>>>>>>>>>>>>>>>>>>>>..<<<<<<<<<<<<<<<<<<<< ----------------");
	}
	
   @RequestMapping(value="/ping", method=RequestMethod.GET)
   public @ResponseBody String printHello() {
      return "ping";
   }
   
   @RequestMapping(value="/demo", method = RequestMethod.GET)
   public String printDemo(ModelMap model) {
      //model.addAttribute("message", "Sostituisco il messaggio");

      return "demo";
   }
   
  
  
}
