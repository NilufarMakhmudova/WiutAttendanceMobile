package uz.wiut.attendancewiut;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class NewStudentFragment extends DialogFragment {
	
	public static final String EXTRA_STUDENT_EMAIL = "uz.wiut.attendancewiut.new_student_email";
	private String mEmailPrefix;
	private EditText mEmailPrefixField;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_student, null);
		mEmailPrefix = new String();
		
		mEmailPrefixField = (EditText) v.findViewById(R.id.editText_new_student);
		mEmailPrefixField.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mEmailPrefix = mEmailPrefixField.getText().toString();
			}

			@Override
			public void afterTextChanged(Editable s) {}});
		
		return new AlertDialog.Builder(getActivity()).setTitle(R.string.new_student_title)
				.setView(v)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_OK);
					}
				})
				.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendResult(Activity.RESULT_CANCELED);
						
					}
				})
				.create();
		
	}
	
	private void sendResult(int resultCode){
		if (getTargetFragment() == null)
			return;
		
		Intent i = new Intent();
		i.putExtra(EXTRA_STUDENT_EMAIL, mEmailPrefix);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
	}
	  
	

}
