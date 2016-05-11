package uz.wiut.attendancewiut;

import java.util.Calendar;
import java.util.Date;

import uz.wiut.attendancewiut.Model.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RescheduleFragment extends Fragment {

	private RescheduledSeminar mRescheduledSeminar;
	//for log
	public static final String TAG = "uz.wiut.attendancewiut.seminar_id_res";
	
		
	//for getting data from calling activity
	public static final String Extra_Seminar_ID_res = "uz.wiut.attendancewiut.seminar_id_res";
	public static final String EXTRA_TEACHING_WEEK = "uz.wiut.attendancewiut.seminar_tw_res";
	public static final String EXTRA_GROUP_NAME = "uz.wiut.attendancewiut.seminar_group_name_res";
	public static final String EXTRA_MODULE_NAME = "uz.wiut.attendancewiut.seminar_module_name_res";

	private static final String DIALOG_DATE_FROM= "date_from";
	private static final String DIALOG_DATE_TO= "date_to";
	private static final String DIALOG_TIME_FROM= "time_from";
	private static final String DIALOG_TIME_TO= "time_to";

	private static final int REQUEST_DATE_FROM = 0;
	private static final int REQUEST_DATE_TO = 1;
	private static final int REQUEST_TIME_FROM = 2;
	private static final int REQUEST_TIME_TO = 3;

	// UI elements
	private TextView mGroup;
	private TextView mModule;
	private TextView mTW;
	private Button mRescheduleFromDate;
	private Button mRescheduleFromTime;
	private Button mRescheduleToDate;
	private Button mRescheduleToTime;
	private EditText mRescheduleReason;
	private DatePickerFragment mDialodDate;
	private TimePickerFragment mDialodTime;

	private int mTWNumber =  1;
	private String mGroupName = "Anything";
	private String mModuleName = "Anything";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		mRescheduledSeminar = new RescheduledSeminar();
		int semIdFromIntent = getActivity().getIntent().getIntExtra(Extra_Seminar_ID_res, 0);
		mTWNumber = getActivity().getIntent().getIntExtra(EXTRA_TEACHING_WEEK, 0);
		Log.d(TAG, new StringBuilder("got tw number").append(mTWNumber).toString());
		mGroupName = getActivity().getIntent().getStringExtra(EXTRA_GROUP_NAME);
		mModuleName = getActivity().getIntent().getStringExtra(EXTRA_MODULE_NAME);
		Date rescheduleDate = new Date();
		mRescheduledSeminar.setSeminarId(semIdFromIntent);
		mRescheduledSeminar.setTeachingWeek(mTWNumber);
		mRescheduledSeminar.setRescheduleDate(rescheduleDate);
		
		
		


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode != Activity.RESULT_OK) return;
		Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
		Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
		switch (requestCode){
		case REQUEST_DATE_FROM: 
			mRescheduledSeminar.setFromDate(date);
			mRescheduleFromDate.setText(DateToDisplay(mRescheduledSeminar.getFromDate()));
			return;

		case REQUEST_DATE_TO: 
			mRescheduledSeminar.setToDate(date);
			mRescheduleToDate.setText(DateToDisplay(mRescheduledSeminar.getToDate())); 
			return;

		case REQUEST_TIME_FROM: 
			mRescheduledSeminar.setFromDate(time);
			mRescheduleFromTime.setText(TimeToDisplay(mRescheduledSeminar.getFromDate()));
			return;

		case REQUEST_TIME_TO: 
			mRescheduledSeminar.setToDate(time);
			mRescheduleToTime.setText(TimeToDisplay(mRescheduledSeminar.getToDate()));
			return;

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){

		View v =inflater.inflate(R.layout.fragment_reschedule, parent, false);

		//display actions bar
		if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityName(getActivity())!=null){
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}

		//user interface elements
		mGroup = (TextView) v.findViewById(R.id.textView_reschedule_group_name_2);
		mModule = (TextView) v.findViewById(R.id.textView_reschedule_module_name_2);
		mTW = (TextView) v.findViewById(R.id.textView_reschedule_teachingweek_number_2);
		mRescheduleFromDate = (Button)v.findViewById(R.id.btn_reschedule_from_date);
		mRescheduleFromTime = (Button)v.findViewById(R.id.btn_reschedule_from_time);
		mRescheduleToDate = (Button)v.findViewById(R.id.btn_reschedule_to_date);
		mRescheduleToTime = (Button)v.findViewById(R.id.btn_reschedule_to_time);
		mRescheduleReason = (EditText) v.findViewById(R.id.editText_reschedule_reason);
		Button mReschedule = (Button) v.findViewById(R.id.btn_reschedule_seminar);

		mGroup.setText(mGroupName);
		mModule.setText(mModuleName);
		mTW.setText(mTWNumber);
		
		//get reschedule reason from edittext
		mReschedule.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String reason = mRescheduleReason.getText().toString();
				mRescheduledSeminar.setRescheduleReason(reason);

				// TODO Upload reschedule record to database

			}
		});

		mRescheduleFromDate.setText("Choose date");
		mRescheduleFromDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog("date", DIALOG_DATE_FROM, REQUEST_DATE_FROM, 0);
			}
		});

		mRescheduleFromTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog("time", DIALOG_TIME_FROM, REQUEST_TIME_FROM, 0);
			}
		});

		mRescheduleToDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog("date", DIALOG_DATE_TO, REQUEST_DATE_TO, 1);
			}
		});

		mRescheduleToTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog("time", DIALOG_TIME_TO, REQUEST_TIME_TO, 1);
			}
		});
		
		//update the view 
		mRescheduleFromDate.setText(DateToDisplay(mRescheduledSeminar.getFromDate()));
		mRescheduleToDate.setText(DateToDisplay(mRescheduledSeminar.getToDate()));
		mRescheduleFromTime.setText(TimeToDisplay(mRescheduledSeminar.getFromDate()));
		mRescheduleToTime.setText(TimeToDisplay(mRescheduledSeminar.getToDate()));

		return v;
	}


	private String DateToDisplay(Date dateToBeDerived){
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeDerived);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		sb.append(day);
		sb.append("/");
		sb.append(month);
		sb.append("/");
		sb.append(year);
		return sb.toString();
	}

	private String TimeToDisplay(Date dateToBeDerived){
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeDerived);
		int hh = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		sb.append(hh);
		sb.append(":");
		sb.append(mm);
		return sb.toString();
	}

	public void showDialog (String type, String tag, int requestCode, int form){
		FragmentManager fm = getActivity().getSupportFragmentManager();

		if (type == "date"){
			switch (form){
			case 0: mDialodDate = DatePickerFragment.newInstance(mRescheduledSeminar.getFromDate());
			case 1: mDialodDate = DatePickerFragment.newInstance(mRescheduledSeminar.getToDate());
			}
			mDialodDate.setTargetFragment(RescheduleFragment.this, requestCode);
			mDialodDate.show(fm, tag);
		}

		if (type == "time"){
			switch (form){
			case 0: mDialodTime = TimePickerFragment.newInstance(mRescheduledSeminar.getFromDate());
			case 1: mDialodTime = TimePickerFragment.newInstance(mRescheduledSeminar.getToDate());
			}
			mDialodTime.setTargetFragment(RescheduleFragment.this, requestCode);
			mDialodTime.show(fm, tag);
		}
	}
	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		switch (item.getItemId()){
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity())!= null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}			
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}

