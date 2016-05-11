package uz.wiut.attendancewiut;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.net.Uri;
import android.util.Log;
import uz.wiut.attendancewiut.Model.*;

public class Fetcher {
	public static final String TAG = "Fetch";
	
	//TODO add time and moduleID to string
	private static final String ENDPOINT = "http://192.168.137.1/API/";
	
	private static final String MODULE_ID = "ModuleID";
	private static final String TIME = "TimeID";	
	private static final String TUTOR_ID = "TutorID";

	private static final String METHOD_GET_STUDENTS = "Students";
	private static final String METHOD_GET_TIMES= "Times";
	private static final String METHOD_GET_MODULES = "Modules";
	private static final String METHOD_GET_GROUPS = "Groups";

	private static final String TIMES_TAG = "Times";

	private static final String USERNAME = "username";

	private static final String PASSWORD = "password";

	private static final String METHOD_GET_TUTORID = "Tutors";
	
	
byte[] getUrlBytes(String urlSpec) throws IOException{
	URL url = new URL(urlSpec);
	HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	Log.i(TAG, "connection started");
		try {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = connection.getInputStream();
		if (connection.getResponseCode() !=HttpURLConnection.HTTP_OK){
			return null;
		}
		
		int bytesRead= 0;
		byte[] buffer = new byte[1024];
		while ((bytesRead = in.read(buffer))>0){
			out.write(buffer, 0, bytesRead);
		}
		out.close();
		return out.toByteArray();		
	} finally {
		connection.disconnect();
	}
}

public String getUrl(String urlSpec) throws IOException {
	Log.i(TAG, "here is the url" + new String(getUrlBytes(urlSpec)));
	return new String(getUrlBytes(urlSpec));
}


public void fetchStudents(int moduleID, int timeId){
	try {
		
		String moduleIdConverted = String.valueOf(moduleID);
		String timeConverted = String.valueOf(timeId);
		String url = Uri.parse(getEndpoint(METHOD_GET_STUDENTS)).buildUpon()
				.appendQueryParameter(MODULE_ID, moduleIdConverted)
				.appendQueryParameter(TIME, timeConverted).build().toString();
		String jsonString = getUrl(url);
		Log.i(TAG, "received JSON" + jsonString);
	} catch (IOException ioe) {
		Log.e(TAG, "failed to fetch students", ioe);
	}
}

public void fetchModules(int TutorID, int timeId){
	try {
		String tutorIdConverted = String.valueOf(TutorID);
		String timeConverted = String.valueOf(timeId);
		String url = Uri.parse(getEndpoint(METHOD_GET_MODULES)).buildUpon()				
				.appendQueryParameter(TIME, timeConverted)
				.appendQueryParameter(TUTOR_ID, tutorIdConverted).build().toString();
		String jsonString = getUrl(url);
		Log.i(TAG, "received JSON" + jsonString);
	} catch (IOException ioe) {
		Log.e(TAG, "failed to fetch modules", ioe);
	}
}

public ArrayList<Time> fetchTimes(){
	ArrayList<Time> times = new ArrayList<Time>(); 
	try {		
		String url = Uri.parse(getEndpoint(METHOD_GET_TIMES)).buildUpon()				
				.build().toString();
		String jsonString = getUrl(url);
		times = parseTimes(jsonString);
		Log.i(TAG, "received JSON" + jsonString);
		Log.i(TAG, "parced JSON" + times);
	} catch (IOException ioe) {
		Log.e(TAG, "failed to fetch times", ioe);
	}
	return times;
}

public void fetchGroups(int moduleID, int timeId){
	try {	
		String moduleIdConverted = String.valueOf(moduleID);
		String timeConverted = String.valueOf(timeId);
		String url = Uri.parse(getEndpoint(METHOD_GET_GROUPS)).buildUpon()
				.appendQueryParameter(TIME, timeConverted)
				.appendQueryParameter(MODULE_ID, moduleIdConverted)
				.build().toString();
		String jsonString = getUrl(url);
		Log.i(TAG, "received JSON" + jsonString);
	} catch (IOException ioe) {
		Log.e(TAG, "failed to fetch times", ioe);
	}
}

public Integer fetchTutorID(String username, String password){
	Integer fetchedTutorID = 0;
	try {		
		String url = Uri.parse(getEndpoint(METHOD_GET_TUTORID)).buildUpon()
				.appendQueryParameter(USERNAME, username)
				.appendQueryParameter(PASSWORD, password)
				.build().toString();
		Log.i(TAG, "formed url" + url);
		String jsonString = getUrl(url);
		Log.i(TAG, "received JSON" + jsonString);		
		fetchedTutorID = parseTutorID(jsonString);		
		Log.i(TAG, "parced JSON" + fetchedTutorID);
	} catch (IOException ioe) {
		Log.e(TAG, "failed to fetch times", ioe);
	}
	return fetchedTutorID;
}

public Integer parseTutorID (String JsonString){
	Integer TutorID = 0;
	try{
		Log.i(TAG, "parser started" + TutorID);
		JSONArray response = new JSONArray(JsonString);
		JSONObject obj = response.getJSONObject(0);
		TutorID = Integer.valueOf(obj.getInt("ID"));
		Log.i(TAG, "parser started" + TutorID);
	} catch (JSONException e) {
		e.printStackTrace();
	} 
	Log.i(TAG, "current tutor id" + TutorID);
	return TutorID;
}

public ArrayList<Time> parseTimes(String JsonString){
	ArrayList<Time> results = new ArrayList<Time>();	
	try {
		Log.i(TAG, "parseTimes started");
		JSONArray times =new JSONArray(JsonString);
		Log.i(TAG, "times array extracted");
		// Iterate over times array
		for (int idx = 0; idx < times.length(); idx++) {

			// Get single time data - a Time
			JSONObject time = (JSONObject) times.get(idx);

			// Summarise time data as a string and add it to
			// result
			Time result = new Time();
			int id = time.getInt("ID");
			String startTimeReceived = time.getString("StartDate");
			String endTimeReceived = time.getString("EndDate");
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startTime = sf.parse(startTimeReceived);
			Date endTime = sf.parse(endTimeReceived);
			result.setID(id);
			result.setStartTime(startTime);
			result.setEndTime(endTime);
			results.add(result);
		}
	} catch (JSONException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return results;

}

//helper method
public String getEndpoint(String method){
	StringBuilder sb = new StringBuilder();
	sb.append(ENDPOINT).append("/").append(method);
	return sb.toString();	
}
}
