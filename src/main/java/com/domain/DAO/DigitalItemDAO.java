package com.domain.DAO;
import org.springframework.stereotype.Component;

import com.domain.POJO.DigitalItemPOJO;


@Component ("digitalItemDAO")
public class DigitalItemDAO extends BaseDAO<DigitalItemPOJO> {
	
	public DigitalItemDAO() {
		this.PropertyImplementationBinder (DigitalItemPOJO.class);
	}
}
