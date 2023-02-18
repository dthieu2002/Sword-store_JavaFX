package core;

public class User {
	// properties 
	private String userName;
	private String password;
	
	//method 
	public User() {}
	public User(String userName, String password) {
		this.userName=userName;
		this.password=password;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User temp = (User)obj;
			if(temp.getUserName().equals(this.getUserName())){
				if(temp.getPassword().equals(this.getPassword())) return true;
			}
		}
		return false;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
