package uz.wiut.attendancewiut;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.Toast;

public class AttendanceListActivity extends SingleFragmentActivity {
	private static final String TAG ="WiutAttendanceSeminarHomeActivity";
	private NewStudentListener newStudentListener;

	// NFC-related variables
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;
	IntentFilter[] mReadTagFilters;
	IntentFilter[] mWriteTagFilters;

	protected Fragment createFragment() {
		ListFragment fragment = new AttendanceListFragment();
		newStudentListener = (NewStudentListener) fragment;
		return fragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// get an instance of the context's cached NfcAdapter
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);


		// if null is returned this demo cannot run. Use this check if the
		// "required" parameter of <uses-feature> in the manifest is not set
		if (mNfcAdapter == null)
		{
			Toast.makeText(this,
					"Your device does not support NFC. Cannot run demo.",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// check if NFC is enabled
		checkNfcEnabled();

		// Handle foreground NFC scanning in this activity by creating a
		// PendingIntent with FLAG_ACTIVITY_SINGLE_TOP flag so each new scan
		// is not added to the Back Stack
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		// Create intent filter to handle NDEF NFC tags detected from inside our
		// application when in "read mode":
		IntentFilter ndefDetected = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		try
		{
			ndefDetected.addDataType("text/plain");
		} catch (MalformedMimeTypeException e)
		{
			throw new RuntimeException("Could not add MIME type.", e);
		}

		// Create intent filter to detect any NFC tag when attempting to write
		// to a tag in "write mode"
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);

		// create IntentFilter arrays:

		mReadTagFilters = new IntentFilter[] { ndefDetected, tagDetected };

	}

	@Override
	public void onNewIntent(Intent intent)
	{
setIntent(intent);
		Log.d(TAG, "onNewIntent: " + intent);


		if (intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED))
		{
			NdefMessage[] msgs = getNdefMessagesFromIntent(intent);
			NdefMessage msg = msgs[0];
			String payload = new String(msg.getRecords()[0]
					.getPayload());
			
			newStudentListener.onNewStudentRead(payload);

		} else if (intent.getAction().equals(
				NfcAdapter.ACTION_TAG_DISCOVERED))
		{
			Toast.makeText(this,
					"This NFC tag currently has no student NDEF data.",
					Toast.LENGTH_LONG).show();
		}
	} 

	NdefMessage[] getNdefMessagesFromIntent(Intent intent)
	{
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)
				|| action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED))
		{
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null)
			{
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++)
				{
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else
			{
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}
		} else
		{
			Log.e(TAG, "Unknown intent.");
			this.finish();
		}
		return msgs;
	}





	@Override
	public void onStart(){
		super.onStart();	
		Log.d(TAG, "onStart() called");
	}


	@Override
	public void onPause()
	{
		super.onPause();
		Log.d(TAG, "onPause: " + getIntent());
		mNfcAdapter.disableForegroundDispatch(this);
	}


	@Override
	public void onResume(){
		super.onResume();


		// Double check if NFC is enabled
		checkNfcEnabled();

		Log.d(TAG, "onResume: " + getIntent());

		
		// Enable priority for current activity to detect scanned tags
		// enableForegroundDispatch( activity, pendingIntent,
		// intentsFiltersArray, techListsArray );
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
				mReadTagFilters, null);
	}
	@Override
	public void onStop(){
		super.onStop();	
		Log.d(TAG, "onStop() called");
	}

	public interface NewStudentListener {
		public void onNewStudentRead(String newStudentId);

	}

	private void checkNfcEnabled()
	{
		Boolean nfcEnabled = mNfcAdapter.isEnabled();
		if (!nfcEnabled)
		{
			new AlertDialog.Builder(this)
			.setTitle(getString(R.string.warning_nfc_is_off))
			.setMessage(getString(R.string.turn_on_nfc))
			.setCancelable(false)
			.setPositiveButton("Update Settings",
					new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog,
						int id)
				{
					startActivity(new Intent(
							android.provider.Settings.ACTION_NFC_SETTINGS));
				}
			}).create().show();
		}
	}

}
