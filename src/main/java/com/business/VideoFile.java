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

	private static Logger logger  = LoggerFactory.getLogger(VideoFile.class);
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
			String[] cmdarray = {"ffmpeg", "-i", videoFile.getPath(), 
					 "-y", "-ss", "2",  videoFile.getPath() + ".jpeg" };
			Process proc = Runtime.getRuntime().exec(cmdarray);
			
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
			String[] cmdarray = {"ffmpeg", "-i", videoFile.getPath()};
			Process proc = Runtime.getRuntime().exec(cmdarray);
			
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
	
	public static boolean transcodingH264 (File source, File dest)
	{
		try {
			String[] cmdarray = {"ffmpeg", "-i", source.getAbsolutePath(), 
					 "-y", "-t", "60", "-vcodec", "libx264", "-acodec", "copy", dest.getAbsolutePath()};
			Process proc = Runtime.getRuntime().exec(cmdarray);
			
			InputStream output = proc.getErrorStream();
			proc.waitFor();
			String s = "";
			while (output.available()  > 0)
				s = s + (char) output.read();
			
			if (dest.exists())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

}
