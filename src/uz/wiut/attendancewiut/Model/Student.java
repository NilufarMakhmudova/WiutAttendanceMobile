package uz.wiut.attendancewiut.Model;
public class Student {
private int mId;
private String mName;
private String mSurname;

public Student(){

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

@Override
public String toString(){
	StringBuilder sb = new StringBuilder();
	sb.append(mName);
	sb.append(" ");
	sb.append(mSurname);	
	return sb.toString();
	}

public void setId(int id) {
	mId = id;
}

}
