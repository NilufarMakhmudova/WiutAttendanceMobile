package uz.wiut.attendancewiut;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimePickerFragment extends DialogFragment {
	private Date mTime;
	public static final String EXTRA_TIME = "uz.wiut.attendancewiut.reschedule_time";

	public static TimePickerFragment newInstance(Date date){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, date);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		mTime = (Date)getArguments().getSerializable(EXTRA_TIME);

		//Create a calendar to get the year, month and day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mTime);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(hour);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener( new TimePicker.OnTimeChangedListener() {

			 @Override
	         public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
	            
	            //retrieving the original crime date from the mTime value with a calendar 
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(mTime);
	            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
	            calendar.set(Calendar.MINUTE, minute);
	            
	            // Translating hourOfDay & minute into a Date object using a calendar, date keeps the same
	            mTime = calendar.getTime();
	            
	            //Update argument to preserve selected value on rotation
	            getArguments().putSerializable(EXTRA_TIME, mTime);
	           
	         }
	         
	      });
			
		

		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(R.string.time_picker)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendResult(Activity.RESULT_OK);

			}
		})
		.create();

	}
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		Intent i = new Intent();
		i.putExtra(EXTRA_TIME, mTime);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
}


