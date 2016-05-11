package uz.wiut.attendancewiut;


import android.support.v4.app.Fragment;

public class RescheduleActivity extends SingleFragmentActivity {
	
	 	@Override
		protected Fragment createFragment() {
			return new RescheduleFragment();
		}

}
