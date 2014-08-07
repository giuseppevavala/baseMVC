package com.business;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domain.POJO.VideoFilePOJO;

public class VideoFile {

	private Logger logger  = LoggerFactory.getLogger(this.getClass());
	private long duration = -1;
	private VideoFilePOJO videoFile = new VideoFilePOJO();
	private File thumbnail = null;
	private int height = -1;
	private int width = -1;
	
	
	public VideoFile(String path) {
		videoFile.setPath(path);
		
		duration = readDuration();
		videoFile.setDuration(duration);
		
		thumbnail = createThumbnail();
		if (thumbnail != null) videoFile.setThumbnail(thumbnail.getAbsolutePath());
		
		try {
			BufferedImage imgThumb = ImageIO.read(thumbnail);
			height = imgThumb.getHeight();
			width = imgThumb.getWidth();
			videoFile.setHeight(height);
			videoFile.setWidth(width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.debug("Video File: " + videoFile.toString());
	}
	
	
	public VideoFile(VideoFilePOJO videoFile) {
		this.videoFile = videoFile;
	}
	
	public VideoFilePOJO getVideoFilePojo()
	{
		return videoFile;
	}
	
	private File createThumbnail() {
		try {
			Process proc = Runtime.getRuntime().exec("ffmpeg -i " + videoFile.getPath() 
					+ " -y -ss " + (long) (duration*0.1) + " " + videoFile.getPath() + ".jpeg" );
			
			proc.waitFor();
			
			File file = new File (videoFile.getPath() + ".jpeg");
			if (file.exists())
				return file;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	private long readDuration() {
		try {
			Process proc = Runtime.getRuntime().exec("ffmpeg -i " + videoFile.getPath());
			
			InputStream output = proc.getErrorStream();

			proc.waitFor();
			String s = "";
			while (output.available()  > 0)
				s = s + (char) output.read();
			
			int inds = s.indexOf("Duration:");
			int inde = s.indexOf("start:");
			String duration = s.substring(inds, inde).replace("Duration:", "").replace(" ", "").replace(",", "");
			duration = duration.substring(0, duration.length() - 3 );
			
			String[] split =  duration.split(":");
			
			int hour = new Integer (split[0]);
			int minute = new Integer (split[1]);
			int second = new Integer (split[2]);
			
			long result = second + (60*minute) + (60 * 60 * hour);
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
