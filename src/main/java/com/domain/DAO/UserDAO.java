package com.domain.DAO;
import org.springframework.stereotype.Component;

import com.domain.POJO.UserPOJO;


@Component ("userDAO")
public class UserDAO extends BaseDAO<UserPOJO> {
	
	public UserDAO() {
		this.PropertyImplementationBinder (UserPOJO.class);
	}
}
