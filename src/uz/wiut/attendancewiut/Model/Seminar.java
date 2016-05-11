package uz.wiut.attendancewiut.Model;
import android.text.format.Time;

public class Seminar {
	private int mId;
	private int mGroupId;
	private int mModuleId;
	private int mTimeId;
	private int mTutorId;
	private String mDayOfWeek;
	private Long mStartTime;
	private String mBuilding;
	private String mRoom;
	
public Seminar(){
	
}

public int getId() {
	return mId;
}

public int getGroupId() {
	return mGroupId;
}

public int getModuleId() {
	return mModuleId;
}

public int getTimeId() {
	return mTimeId;
}

public int getTutorId() {
	return mTutorId;
}

public String getDayOfWeek() {
	return mDayOfWeek;
}

public void setDayOfWeek(String dayOfWeek) {
	mDayOfWeek = dayOfWeek;
}

public Long getStartTime() {
	return mStartTime;
}

public void setStartTime(Long startTime) {
	mStartTime = startTime;
}

public String getBuilding() {
	return mBuilding;
}

public void setBuilding(String building) {
	mBuilding = building;
}

public String getRoom() {
	return mRoom;
}

public void setRoom(String room) {
	mRoom = room;
}

public void setGroupId(int groupId) {
	mGroupId = groupId;
}

public void setModuleId(int moduleId) {
	mModuleId = moduleId;
}

public void setTimeId(int timeId) {
	mTimeId = timeId;
}

public void setTutorId(int tutorId) {
	mTutorId = tutorId;
}
	
}