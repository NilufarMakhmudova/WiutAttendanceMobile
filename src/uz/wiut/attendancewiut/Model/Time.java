package uz.wiut.attendancewiut.Model;

import java.util.Date;

public class Time {

	private int mID;
	private Date mStartTime;
	private Date mEndTime;
	public int getID() {
		return mID;
	}
	public void setID(int iD) {
		mID = iD;
	}
	public Date getStartTime() {
		return mStartTime;
	}
	public void setStartTime(Date startTime) {
		mStartTime = startTime;
	}
	public Date getEndTime() {
		return mEndTime;
	}
	public void setEndTime(Date endTime) {
		mEndTime = endTime;
	}
}
