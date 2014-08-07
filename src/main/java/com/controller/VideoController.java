package com.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.domain.POJO.ResponsePOJO;
import com.domain.service.DigitalItemService;
import com.exception.VideoException;


@Controller
public class VideoController {

	private Logger myLogger;
		
	@Value ("${video.folder.upload}")
	private String resourceFolder;
			
	@Autowired
	ServletContext context;
	
	@Autowired
	DigitalItemService digitalItemService;
	
	/** INIT ***/
	public VideoController()
	{
		myLogger = LoggerFactory.getLogger(this.getClass());
		myLogger.info (" ************************************** CARICO VideoController  ******************************");
	}
	
		
	/**
	 * Upload di un contenuto.
	 * 
	 * @param  File (name=bin)
	 * @param  String (name=generateKey)
	 * @return        string JsonFormat.
	 */
	@RequestMapping(value = "/video", method = RequestMethod.POST)
	public @ResponseBody ResponsePOJO handleFileUpload(
	        @RequestParam(value = "video") CommonsMultipartFile fileUpload
	       ) throws VideoException          
	{
		File destDirectory;
		ResponsePOJO resp = new ResponsePOJO();

		resp.setError(true);
		try {
			destDirectory = new File(resourceFolder);
			
			// If the directory non exist create it, and set write permission to everyone
			if (!destDirectory.exists())
				destDirectory.mkdirs();
			destDirectory.setWritable(true, false);
			
			myLogger.info("Storing File...");
			
			if (fileUpload != null)
			{
				FileItem fileItem = fileUpload.getFileItem();
				File destinationFile = new File(destDirectory, fileItem.getName());
				fileItem.write(destinationFile);
				
				myLogger.info("Stored File");
				resp.setError(false);
			}
			else
			{
				throw new VideoException("No file uploaded");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new VideoException(ex.getMessage());
		}
		
		return resp;
	}
	
	
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public String getListFile (
			@RequestParam (value = "digitalItemId", required=false) Integer digitalItemId,
			@RequestParam (value = "videoId", required=false) Integer videoId,
			ModelMap model )          
	{
		if ((digitalItemId != null) && (videoId != null))
		{
			model.addAttribute("videoUrl", "../rest/stream?digitalItemId=" + digitalItemId + "&videoId=" + videoId);
			return "video";
		}
		else
		{
			context.setAttribute("digitalItemService", digitalItemService);
			return "VideoList";
		}
	}
	
	
	
}
