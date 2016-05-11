package uz.wiut.attendancewiut;


import android.support.v4.app.Fragment;


public class MainActivity extends SingleFragmentActivity {
	public static final String Extra_TutorID = "uz.wiut.attendancewiut.tutorId";
	public static final String Extra_TimeID = "uz.wiut.attendancewiut.timeId";
	
	
	@Override
	protected Fragment createFragment() {
		return new MainFragment();
	}
    
}
