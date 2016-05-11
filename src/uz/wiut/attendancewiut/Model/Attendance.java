package uz.wiut.attendancewiut.Model;



import java.util.Date;

import android.text.format.Time;


	public class Attendance {
		private int mSeminarId;
		private int mTutorId;
		private int mStudentId;
		private String mStatus;
		private int mTeachingWeek;
		private Date mDate;
		private Time mTime;
		private Boolean sIsRescheduled;

		public Attendance (){
			mDate = new Date();
		}

		public int getSeminarId() {
			return mSeminarId;
		}

		
		public int getTutorId() {
			return mTutorId;
		}

		
		public int getStudentId() {
			return mStudentId;
		}

	
		public String getStatus() {
			return mStatus;
		}

		public void setStatus(String status) {
			mStatus = status;
		}

		public int getTeachingWeek() {
			return mTeachingWeek;
		}

		public void setTeachingWeek(int teachingWeek) {
			mTeachingWeek = teachingWeek;
		}

		public Date getDate() {
			return mDate;
		}

		public void setDate(Date date) {
			mDate = date;
		}

		public Time getTime() {
			return mTime;
		}

		public void setTime(Time time) {
			mTime = time;
		}

		public Boolean getIsRescheduled() {
			return sIsRescheduled;
		}
	}


