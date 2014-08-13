package com.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

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

import com.business.VideoFile;
import com.domain.POJO.DigitalItemPOJO;
import com.domain.POJO.ResponsePOJO;
import com.domain.POJO.VideoFilePOJO;
import com.domain.service.DigitalItemService;
import com.domain.service.VideoFileService;
import com.exception.VideoException;


@Controller
public class VideoController {

	private Logger myLogger;
		
	@Value ("${video.folder.upload}")
	private String resourceFolder;
	
	@Value ("${video.folder.output}")
	private String outputFolder;
			
	@Autowired
	ServletContext context;
	
	@Autowired
	DigitalItemService digitalItemService;
	
	@Autowired
	VideoFileService videoFileService;
	
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
			ModelMap model ) throws UnsupportedEncodingException          
	{
		context.setAttribute("digitalItemService", digitalItemService);
		return "browser";
	}
	
	
	@RequestMapping(value = "/video/transcode", method = RequestMethod.GET)
	public @ResponseBody String transcodeVideo (
			@RequestParam (value = "digitalItemId", required=true) Integer digitalItemId,
			@RequestParam (value = "width", required=true) Integer width
		 )          
	{
		DigitalItemPOJO item = digitalItemService.getFromId(digitalItemId);
		VideoFilePOJO videoFileSrc = item.getVideoFile().get(0);

		VideoFile video = VideoFile.createVideo(width, 
				new File(videoFileSrc.getPath()), 
				new File(outputFolder + File.separator + item.getTitolo() + UUID.randomUUID() + ".mp4"));
		
		List<VideoFilePOJO> listVideo = item.getVideoFile(); 
		listVideo.add (video.getVideoFilePojo());
		
		videoFileService.addVideoFile(video.getVideoFilePojo());
		digitalItemService.update(item);
		
		return "<p>Transcoding completato con successo</p><meta http-equiv=\"Refresh\" content=\"5;url=../video\">";
	}
	
	
}
