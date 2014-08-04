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

import org.apache.commons.io.FileUtils;
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

	@PostConstruct
    public void init() {
		long period = 1000;
		
		myLogger.debug("-------------- >>>>>>>>>>>>>>>>>>>>>> Ingestion Business <<<<<<<<<<<<<<<<<<<< ----------------");
		(new File(outputFolder)).mkdirs();
		(new File(inputFolder)).mkdirs();
		
		
		Timer ingestionJob = new Timer();
		TimerTask task = new TimerTask() {
		  public void run() {
			  lookingForNewDigitalItem();
			  if (listFifo.size() > 0) ingestionFile();
		  }

		};
		ingestionJob.scheduleAtFixedRate(task, period, period);
    }
	
	protected void ingestionFile() {
		
		for (String fileName : listFifo) {
			myLogger.debug("Ingestion: " + fileName);
			try {
				List<VideoFilePOJO> listVideoFile = new ArrayList<VideoFilePOJO>();
				String destFileName = moveFile(fileName);
				
				VideoFilePOJO videoFile = new VideoFilePOJO();
				videoFile.setPath(destFileName);
				
				DigitalItemPOJO item = new DigitalItemPOJO();
				item.setTitolo(FilenameUtils.getBaseName(fileName));
				

				listVideoFile.add(videoFile);
				item.setVideoFile(listVideoFile);	
				
				videoFileService.addVideoFile(videoFile);
				digitalItemService.addDigitalItem(item);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		listFifo.clear();
	}

	private String moveFile(String fileName) throws IOException {
		File destFile = new File (outputFolder + "/" +FilenameUtils.getBaseName(fileName) + "-"
				+ UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName));
		
		File file = new File ( fileName );
		FileUtils.moveFile(file, destFile );
		
		return destFile.getAbsolutePath();
	}

	protected void lookingForNewDigitalItem() {
		File[] list = ((new File(inputFolder)).listFiles());
		
		if (list != null) 
		{
			for (File file : list) {
				String fileAbsPath = file.getAbsolutePath();
				if (!listFifo.contains(fileAbsPath))
				{
					myLogger.debug("NEW FILE " + file);
					listFifo.add(fileAbsPath);
				}
			}
		}
	}
	
	

}