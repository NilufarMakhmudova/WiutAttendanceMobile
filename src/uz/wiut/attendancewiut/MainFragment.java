package uz.wiut.attendancewiut;

import java.util.ArrayList;


import uz.wiut.attendancewiut.Model.Module;
import uz.wiut.attendancewiut.Model.Seminar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainFragment extends Fragment {
	private int mTutorId;
	private Seminar mSeminar;
	private int mModuleId;
	private int mTimeId;
	public static final String EXTRA_TUTOR_ID = "uz.wiut.attendancewiut.tutor_id";
	//needed to display only the modules and seminars the tutor teaches 
	private static final String TAG = "WiutAttendanceSeminarMainActivity";
	private ArrayList<Module> mTutorsModules;
	private ArrayList<Seminar> mTutorSeminars;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		Log.d(TAG, "main created");
		new FetchModules().execute();
		mTutorId = getActivity().getIntent().getIntExtra(EXTRA_TUTOR_ID, 0);
		//TODO 
	    //send mTutorId to the server and download mTutorsModules, mTutorSeminars and relevant groups
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{ super.onCreateOptionsMenu(menu, inflater);
	inflater.inflate(R.menu.main, menu);
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode != Activity.RESULT_OK) return;
			
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		
		View v =inflater.inflate(R.layout.fragment_main, parent, false);
		//get reschedule reason from edittext
		
		Button mStartSeminarButton = (Button) v.findViewById(R.id.btn_start_seminar);
        Button mRescheduleSeminarButton = (Button) v.findViewById(R.id.btn_reschedule_seminar);
        
        //retrieve extra
       //	mTutorId = getActivity().getIntent().getIntExtra(Extra_TutorID, 0);
       	
        
        Spinner group_spinner = (Spinner) v.findViewById(R.id.spinner_seminar_group_name);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		     R.array.groups_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		group_spinner.setAdapter(adapter);
		group_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		       //TODO
				new FetchGroups().execute();
				//download seminar, group, module, objects simultaneously
			String selectedGroup = parent.getItemAtPosition(pos).toString();
		      // mSeminar.setGroupId((int));
		    }
			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // TODO make a toast prompting for user input
		    }

			
		});
		
		Spinner module_spinner = (Spinner) v.findViewById(R.id.spinner_seminar_module_name);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterModule = ArrayAdapter.createFromResource(getActivity(),
		     R.array.modules_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterModule.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		module_spinner.setAdapter(adapterModule);
		module_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
				//TODO fetch groups that have this module
				new FetchGroups().execute();
		    }
			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }

			
		});
		
		Spinner teaching_week_spinner = (Spinner) v.findViewById(R.id.spinner_seminar_teachingweek_number);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapterTeachingWeek = ArrayAdapter.createFromResource(getActivity(),
		     R.array.teaching_weeks_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterTeachingWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		teaching_week_spinner.setAdapter(adapterTeachingWeek);
		teaching_week_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        // parent.getItemAtPosition(pos)
		    }
			@Override
		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }

			
		});
	
        mStartSeminarButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int semID = 0;
				//start AttendanceActivity
				Intent i = new Intent (getActivity(), AttendanceListActivity.class);
				i.putExtra(AttendanceListFragment.Extra_Seminar_ID, semID);
				startActivity(i);

			}
		});
        
        mRescheduleSeminarButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int semID = 10;
				String groupName = "4BIS2";
				String moduleName = "WAD";
				int tw = 2;
				Log.d(TAG, "values created");
				Intent i = new Intent (getActivity(), RescheduleActivity.class);
				Log.d(TAG, "intent created");
				i.putExtra(RescheduleFragment.Extra_Seminar_ID_res, semID);
				Log.d(TAG, "semID put");
				i.putExtra(RescheduleFragment.EXTRA_GROUP_NAME, groupName);
				Log.d(TAG, "groupName put");
				i.putExtra(RescheduleFragment.EXTRA_MODULE_NAME, moduleName);
				Log.d(TAG, "moduleName put");
				i.putExtra(RescheduleFragment.EXTRA_TEACHING_WEEK, tw);
				Log.d(TAG, "tw put");
				startActivity(i);
			}
		});
        
        return v;
    	
    }


   
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.log_out) {
        	//TODO log out
            return true;
        }
        return super.onOptionsItemSelected(item);
    
		
		}
    
    
    private class FetchModules extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params){
			 new Fetcher().fetchTimes();
			 //Pass tutorid and time id
			 new Fetcher().fetchModules(1, 6);
			 return null;
			}
		}
    
    private class FetchGroups extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params){
			 new Fetcher().fetchTimes();
			 //Pass tutorid and time id
			 new Fetcher().fetchGroups(mModuleId, mTimeId);
			 return null;
			}
		}
	}



