package baseMVC;

import java.io.IOException;

public class TestFfmpeg {

	public static void main (String[] args)
	{
		try {
//			Process proc = Runtime.getRuntime().exec("ffmpeg -i /tmp/filmSbagliato.avi -s 720x480 -c:a copy /tmp/output.avi");
			Process proc = Runtime.getRuntime().exec("ffmpeg -i /tmp/film.avi -ss 00:00:30.0 -c copy -t 00:00:10.0 /tmp/output.avi");
			
			proc.waitFor();
			
			System.out.println ("Finito " + proc.exitValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
