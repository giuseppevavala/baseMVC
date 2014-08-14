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
	
	
	// ffmpeg -i input.mp4 -filter:v scale=720:-1 -c:a copy output.mp4
	public static VideoFile createVideo (int width, File src, File dest )
	{
		ConsumeOutputThread thread = null;
		try {
			String[] cmdarray = {"ffmpeg", "-i", src.getAbsolutePath(), 
					"-y", "-filter:v", "scale=" + width + ":-1", "-c:a", "copy", dest.getAbsolutePath()};
			
			
			logger.debug("TRANSCODING START");
			Process proc = Runtime.getRuntime().exec(cmdarray);
			thread = consumeOutput(proc);
			proc.waitFor();
			logger.debug("TRANSCODING FINISHED");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		thread.stopThread();
		if (dest.exists())
			return new VideoFile (dest.getAbsolutePath());		
		else
			return null;
	}
	
	public static boolean transcodingH264Trailer (File source, File dest)
	{
		ConsumeOutputThread thread = null;
		try {
			
			String[] cmdarray = {"ffmpeg", "-i", source.getAbsolutePath(), 
					 "-y", "-t", "60", "-vcodec", "libx264", "-acodec", "copy", dest.getAbsolutePath()};
			
			logger.debug("TRANSCODING START");
			Process proc = Runtime.getRuntime().exec(cmdarray);
			thread = consumeOutput(proc);
			proc.waitFor();
			logger.debug("TRANSCODING FINISHED");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		thread.stopThread();
		if (dest.exists())
			return true;
		else
			return false;
	}


	public static boolean transcodingH264 (File source, File dest) {
		ConsumeOutputThread thread = null;
		try {
			
			String[] cmdarray = {"ffmpeg", "-i", source.getAbsolutePath(), 
					 "-y", "-vcodec", "libx264", "-acodec", "copy", dest.getAbsolutePath()};
			logger.debug("TRANSCODING START");
			Process proc = Runtime.getRuntime().exec(cmdarray);
			thread = consumeOutput(proc);
			proc.waitFor();
			logger.debug("TRANSCODING FINISHED");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		thread.stopThread();
		
		if (dest.exists())
			return true;
		else
			return false;
	}
	
	private static ConsumeOutputThread consumeOutput (final Process proc)
	{
		ConsumeOutputThread thread = new ConsumeOutputThread(proc, logger);
		thread.start();
		return thread;
	}
}

class ConsumeOutputThread extends Thread
{
	private Process proc;
	private boolean run = true;
	private Logger logger;

	public ConsumeOutputThread(Process proc, Logger logger) {
		this.proc = proc;
		this.logger = logger;
	}
	
	@Override
	public void run() {
		super.run();
		
		InputStream error = proc.getErrorStream();
		InputStream output = proc.getInputStream();
		while (run )
		{
			try {
				String stringOutput = "";
				String stringError = "";
				
				while (error.available() > 0)
					stringOutput = stringOutput + (char) error.read();
				while (output.available() > 0)
					stringError = stringError + (char)  output.read();
				
				if (stringOutput.length() > 0)logger.debug(stringOutput);
				if (stringError.length() > 0)logger.debug(stringError);
				
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	
	public void stopThread (){
		run = false;
	}
}
