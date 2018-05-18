package testsocket;

public class User_more {
	/**
	  * 用户信息类
	  * 用于记录用户个人信息:姓名以及IP
	  */
	private String name;
	private String ip;
	public User_more(String name, String ip) {
	this.name = name;
	this.ip = ip;
	}
	public String getName() {
	 return name;
	}
	public void setName(String name) {
	 this.name = name;
	}
	public String getIp() {
	 return ip;
	}
	public void setIp(String ip) {
	this.ip = ip;
	}
}
