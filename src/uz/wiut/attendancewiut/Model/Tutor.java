package uz.wiut.attendancewiut.Model;

public class Tutor {
	private int mId;
	private String mName;
	private String mSurname;
	private String mPassword;
	
	public Tutor ( ){
			
		
	}

	public int getId() {
		return mId;
	}

	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getSurname() {
		return mSurname;
	}

	public void setSurname(String surname) {
		mSurname = surname;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}
	

}
