package uz.wiut.attendancewiut.Model;
import java.util.Date;

public class RescheduledSeminar {
	private int mId;
	private int mSeminarId;
	public int getSeminarId() {
		return mSeminarId;
	}


	public void setSeminarId(int seminarId) {
		mSeminarId = seminarId;
	}


	private int mTeachingWeek;
	private Date mFromDate;
	private Date mToDate;
	private String mToBuilding;
	private String mToRoom;
	private Date mRescheduleDate;
	private String mRescheduleReason;


public RescheduledSeminar( ){
	mFromDate = new Date();
	
	mToDate = new Date();
	
}


public int getId() {
	return mId;
}





public int getTeachingWeek() {
	return mTeachingWeek;
}


public void setTeachingWeek(int teachingWeek) {
	mTeachingWeek = teachingWeek;
}


public Date getFromDate() {
	return mFromDate;
}


public void setFromDate(Date fromDate) {
	mFromDate = fromDate;
}


public Date getToDate() {
	return mToDate;
}


public void setToDate(Date toDate) {
	mToDate = toDate;
}

public String getToBuilding() {
	return mToBuilding;
}


public void setToBuilding(String toBuilding) {
	mToBuilding = toBuilding;
}


public String getToRoom() {
	return mToRoom;
}


public void setToRoom(String toRoom) {
	mToRoom = toRoom;
}


public Date getRescheduleDate() {
	return mRescheduleDate;
}


public void setRescheduleDate(Date rescheduleDate) {
	mRescheduleDate = rescheduleDate;
}


public String getRescheduleReason() {
	return mRescheduleReason;
}


public void setRescheduleReason(String rescheduleReason) {
	mRescheduleReason = rescheduleReason;
}
}
