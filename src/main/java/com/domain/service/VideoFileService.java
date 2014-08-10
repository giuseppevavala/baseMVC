package com.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.DAO.VideoFileDAO;
import com.domain.POJO.VideoFilePOJO;

@Service("videoFileSvc")
public class VideoFileService {
      
    @Autowired
    private VideoFileDAO videoFileDAO;

    public void addVideoFile (VideoFilePOJO videoFile)
   	{
    	videoFileDAO.addObject(videoFile);
   	}
    
    public VideoFilePOJO getFromId(int videoFileId) {
		return videoFileDAO.getObjWithLikeCondition("id", videoFileId);
	}
}
