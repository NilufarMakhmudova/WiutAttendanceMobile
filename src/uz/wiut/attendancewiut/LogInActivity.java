package uz.wiut.attendancewiut;

import java.util.ArrayList;
import java.util.Date;

import uz.wiut.attendancewiut.Model.*;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


//this class is responsible for logging tutor in by checking username and password
//it fetches available academic year records when the activity is first created
//it first get the user input and calls AuthenticateTask to send the input to a webservice
//if the input is correct the tutorID should be fetched
//if the input is incorrect asks the user to enter new combination
//chooses the current semester record from fetched times and passes the id of the semester with intents


public class LogInActivity extends Activity {
	private static final String TAG ="WiutAttendanceSeminarHomeActivity";
	ArrayList<Time> mTimes;
	private String mUsername;
	private String mPassword;
	private int mTutorID = 0;
	private int mTimeID=0;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_log_in);
	        // get available academic year and semester records from the database
	        new FetchTimes().execute();
	        
	        final EditText mEditTextUsername = (EditText)findViewById(R.id.editTextUsername);
	        
	        mEditTextUsername.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					mUsername = mEditTextUsername.getText().toString();
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
	        	
	        });
	        final EditText mEditTextPassword = (EditText)findViewById(R.id.editTextPassword);
	        mEditTextPassword.addTextChangedListener(new TextWatcher() {

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					mPassword = mEditTextPassword.getText().toString();
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
	        	
	        });
		       
	        
	        Button mLogInButton = (Button)findViewById(R.id.btn_log_in);
	        mLogInButton.setOnClickListener(new View.OnClickListener() {
	        	@Override
				public void onClick(View v) {
	        		new LogInTask().execute();		
	        		
				}
	        	
	        });
	        
	 }
	 
	 @Override
	   public void onStart(){
		   super.onStart();	
		   Log.d(TAG, "onStart() called");
	   }
	    @Override
	   public void onPause(){
		   super.onPause();	 
		   Log.d(TAG, "onPause() called");
	   }
	    @Override
	   public void onResume(){
		   super.onResume();
		   Log.d(TAG, "onResume() called");
	   }
	    @Override
	   public void onStop(){
		   super.onStop();	
		   Log.d(TAG, "onStop() called");
	   }
	    
	    private class FetchTimes extends AsyncTask<Void, Void, ArrayList<Time>> {
			@Override
			protected ArrayList<Time> doInBackground(Void... params){
				return new Fetcher().fetchTimes();				
				}
			@Override
	    	protected void onPostExecute(ArrayList<Time> times){
	    		mTimes= times;
	    		Log.i(TAG, "my time records" + mTimes);
	    		if (!times.isEmpty()) {
					 Date now = new Date();
					 for (Time time :times ){
						 if (now.after(time.getStartTime()) && now.before(time.getEndTime())){
							 mTimeID = time.getID();
							 Log.i(TAG, "got TimeID" + mTimeID);
						 }
					 }
	        		}
	    		
	    	}
			}
	    
	    private class LogInTask extends AsyncTask<Void, Void, Integer> {
	    	@Override
	    	protected Integer doInBackground(Void... params){	    		
				return new Fetcher().fetchTutorID(mUsername, mPassword);
				}
	    	
	    	@Override
	    	protected void onPreExecute(){
	    		super.onPreExecute();
	    		 
                Toast.makeText(getBaseContext(), "Authorizing...",Toast.LENGTH_SHORT).show();
        
	    	}
	    	
	    	@Override
	    	protected void onPostExecute(Integer TutorID){
	    		mTutorID= TutorID.intValue();
	    		Log.i(TAG, "my tutor id" + mTutorID);
	    		if (TutorID.compareTo(0)>0) {
					//start AttendanceActivity
					Intent i = new Intent (LogInActivity.this, MainActivity.class);
					i.putExtra(MainActivity.Extra_TutorID, mTutorID);
					Log.i(TAG, "put extra tutor id" + mTutorID);
					i.putExtra(MainActivity.Extra_TimeID, mTimeID);
					Log.i(TAG, "put extra time id" + mTimeID);
					Log.d(TAG, "log in intent");
					startActivity(i);
	        		}
	    		else
                {
                      Toast.makeText(getBaseContext(), "Not valid username/password ",Toast.LENGTH_SHORT).show();
                }
	    	}
			
			}


}
