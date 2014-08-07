package baseMVC;

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
