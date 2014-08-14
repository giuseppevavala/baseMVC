package com.business;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.domain.POJO.DigitalItemPOJO;
import com.domain.POJO.VideoFilePOJO;
import com.domain.service.DigitalItemService;
import com.domain.service.VideoFileService;

@Component
public class Ingestion
{
	private Logger myLogger  = LoggerFactory.getLogger(this.getClass());
	private LinkedList<String> listFifo = new LinkedList<String>();
	
	@Autowired
	DigitalItemService digitalItemService;
	@Autowired
	VideoFileService videoFileService;
	
	@Value ("${video.folder.upload}")
	private String inputFolder;
	
	@Value ("${video.folder.output}")
	private String outputFolder;
	
	@Value ("${debug}")
	private boolean isDebug;

	@PostConstruct
    public void init() {
		final long period = 1000;
		
		myLogger.debug("-------------- >>>>>>>>>>>>>>>>>>>>>> Ingestion Business <<<<<<<<<<<<<<<<<<<< ----------------");
		(new File(outputFolder)).mkdirs();
		(new File(inputFolder)).mkdirs();
		
		Timer ingestionJob = new Timer();
		TimerTask task = new TimerTask() {
		  public void run() {
			  myLogger.debug("Start task");
			  while (true)
			  {
				try {
					Thread.sleep(period);
					lookingForNewDigitalItem();
					if (listFifo.size() > 0) 
						ingestionFile();
				} catch (Exception e) {
					e.printStackTrace();
					listFifo.clear();
				}
			  }
		  }

		};
		ingestionJob.schedule(task, 1000); 
    }
	
	protected void ingestionFile() {
		
		for (String fileName : listFifo) {
			myLogger.debug("Ingestion: " + fileName);
			try {
				List<VideoFilePOJO> listVideoFile = new ArrayList<VideoFilePOJO>();
				String destFileName = transcoding(fileName);
				
				if (destFileName != null)
				{
					VideoFile video = new VideoFile(destFileName);
					VideoFilePOJO videoFile = video.getVideoFilePojo();
					
					DigitalItemPOJO item = new DigitalItemPOJO();
					item.setTitolo(FilenameUtils.getBaseName(fileName));
					
					
					listVideoFile.add(videoFile);
					item.setVideoFile(listVideoFile);	
					
					videoFileService.addVideoFile(videoFile);
					digitalItemService.addDigitalItem(item);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		listFifo.clear();
	}

	private String transcoding(String fileName) throws IOException {
		File destFile = new File (outputFolder + "/" +FilenameUtils.getBaseName(fileName) + "-"
				+ UUID.randomUUID() + ".mp4");
		
		File file = new File ( fileName );
		
		boolean result;
		if (isDebug)
			result = VideoFile.transcodingH264Trailer(file, destFile);
		else
			result = VideoFile.transcodingH264(file, destFile);
		if (result)
		{
			file.delete();
			return destFile.getAbsolutePath();
		}
		else
			return null;
	}

	protected void lookingForNewDigitalItem() {
		File[] list = ((new File(inputFolder)).listFiles());
		
		if (list != null) 
		{
			for (File file : list) {
				String fileAbsPath = file.getAbsolutePath();
				if ((!listFifo.contains(fileAbsPath)) && (!FilenameUtils.getBaseName(file.getAbsolutePath()).startsWith(".")))
				{
					myLogger.debug("NEW FILE " + file);
					listFifo.add(fileAbsPath);
				}
			}
		}
	}
	
	

}