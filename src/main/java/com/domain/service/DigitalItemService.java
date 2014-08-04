package com.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.DAO.DigitalItemDAO;
import com.domain.POJO.DigitalItemPOJO;

@Service("digitalItemSvc")
public class DigitalItemService {
      
    @Autowired
    private DigitalItemDAO digitalItemDAO;
    
    public void addDigitalItem (DigitalItemPOJO digitalItem)
   	{
    	digitalItemDAO.addObject(digitalItem);
   	}
    
    public List<DigitalItemPOJO> getAll ()
   	{
    	return digitalItemDAO.getAll();
   	}

	public DigitalItemPOJO getFromId(int digitalItemId) {
		return digitalItemDAO.getObjWithLikeCondition("id", digitalItemId);
	}
}
