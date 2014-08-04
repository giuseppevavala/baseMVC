package com.domain.DAO;
import org.springframework.stereotype.Component;

import com.domain.POJO.VideoFilePOJO;


@Component ("videoFileDAO")
public class VideoFileDAO extends BaseDAO<VideoFilePOJO> {
	
	public VideoFileDAO() {
		this.PropertyImplementationBinder (VideoFilePOJO.class);
	}
}
