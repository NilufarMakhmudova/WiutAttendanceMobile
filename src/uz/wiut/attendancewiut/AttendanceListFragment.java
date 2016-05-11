package uz.wiut.attendancewiut;

import java.util.ArrayList;

import uz.wiut.attendancewiut.AttendanceListActivity.NewStudentListener;
import uz.wiut.attendancewiut.Model.Student;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class AttendanceListFragment extends ListFragment implements NewStudentListener {
	// all students registered students
	private ArrayList<Student> mRegisteredStudents;
	private ArrayList<Student> mPresentStudents;
	private static final String DIALOG_NEW_STUDENT = "new student";
	public static final String Extra_Seminar_ID= "uz.wiut.attendancewiut.seminar_id";
	private int mSeminarId;	
	private static final int REQUEST_STUDENT_EMAIL = 0;
	private static final String TAG = "AttendanceListActivity";



	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.attendances_title);
		//call background fetcher
		new FetchItemsTask().execute();
		mPresentStudents = new ArrayList<Student>();
		Student a = new Student();
		a.setName("Nilufar");
		a.setSurname("Makhmudova");
		Student b = new Student();
		b.setName("Shirin");
		b.setSurname("Nigmanova");
		Student c = new Student();
		c.setName("Khurshida");
		c.setSurname("Makhmudova");

		mPresentStudents.add(a);
		mPresentStudents.add(b);
		mPresentStudents.add(c);

		ArrayAdapter<Student> adapter =
				new ArrayAdapter<Student>(getActivity(), 
						android.R.layout.simple_list_item_1,
						mPresentStudents);
		setListAdapter(adapter);

		mSeminarId= getActivity().getIntent().getIntExtra(Extra_Seminar_ID, 0);
		//TODO download the list of all students registered to the module



	}




	@Override 
	public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_attendance_list, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.menu_item_new_student:
			Student student = new Student();
			// display dialog with edittext
			FragmentManager fm = getActivity().getSupportFragmentManager();
			NewStudentFragment dialog = new NewStudentFragment();
			dialog.setTargetFragment(AttendanceListFragment.this, REQUEST_STUDENT_EMAIL);
			dialog.show(fm, DIALOG_NEW_STUDENT);
			return true;
		case R.id.menu_item_done:
			//TODO finish attendance; upload records to the database
			//TODO redirect to main activity
			return true;
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity())!= null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}			return true;
		default:
			return super.onOptionsItemSelected(item);

		}

	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(android.R.id.list, parent, false);
		if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
			//Enable navigation to parent activity
			if(NavUtils.getParentActivityName(getActivity())!=null){
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		return v;
	}*/

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode != Activity.RESULT_OK) return;
		if (requestCode == REQUEST_STUDENT_EMAIL){
			String emailPrefix = (String) data.getStringExtra(NewStudentFragment.EXTRA_STUDENT_EMAIL);
			//TODO
			//search database for the prefix
			//add the found student to mPresentStudents
			Toast.makeText(getActivity(),
					new StringBuilder("new student added!").append(emailPrefix).toString(),
					Toast.LENGTH_LONG).show();

		}
	}
	/* Called when the activity will start interacting with the user. */

	@Override
	public void onResume()
	{
		super.onResume();
		//update the view
		((ArrayAdapter<Student>)getListAdapter()).notifyDataSetChanged();


	}







	@Override
	public void onNewStudentRead(String newStudentId) {
		
		/*//search for the list of students registered to this module
		Student mNfcStudent = new Student();

		int id = Integer.parseInt(newStudentId);

		//mNfcStudent = mRegisteredStudents.get(id);
		mNfcStudent.setId(id);
		//get name and surname
		mNfcStudent.setName("Adkham");
		mNfcStudent.setSurname("Makhmudov");
		mPresentStudents.add(mNfcStudent);
*/

		Toast.makeText(getActivity(),
				new StringBuilder("new student added!").append(newStudentId).toString(),
				Toast.LENGTH_LONG).show();
	}

	private class FetchItemsTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params){
			 new Fetcher().fetchStudents(67, 6);
			return null;
			}
		}
	}



