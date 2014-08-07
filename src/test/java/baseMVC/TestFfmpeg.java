package baseMVC;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import au.notzed.jjmpeg.AVFormatContext;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;
import au.notzed.jjmpeg.io.JJMediaReader;

import javax.media.*;

import com.business.VideoFile;

public class TestFfmpeg {

	public static void main (String[] args)
	{
		VideoFile video = new VideoFile("/home/giuseppe/Video/video.avi");
		System.out.println (video.getVideoFilePojo().toString());
		video = new VideoFile("/home/giuseppe/Video/video.mp4");
		System.out.println (video.getVideoFilePojo().toString());
	}


}
