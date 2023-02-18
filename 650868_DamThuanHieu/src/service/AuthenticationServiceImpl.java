package service;

import java.util.List;

import core.HRMDB;
import core.User;


public class AuthenticationServiceImpl implements AuthenticationService{

	@Override
	public boolean checkUserLogin(User user) {
		HRMDB hrmDB = new HRMDB("jdbc:ucanaccess://database/hrm.accdb","", "");
		// get user list 
		List<User> userList = hrmDB.getUserList();
		
		// check user
		for(User tmp:userList) {
			if(user.equals(tmp)) return true;
		}
		
		return false;
	}

}
