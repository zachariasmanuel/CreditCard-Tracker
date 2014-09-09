package com.example.creditcardtracker;

import java.io.IOException;
import java.util.ArrayList;

import com.example.Utils.DBUtils.DbUtils;
import com.example.Utils.DBUtils.SmsDatabaseAdapter;
import com.sv.creditcardtracker.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CreditCardMain extends Activity {
	Button mSummaryButton;
	DbUtils db;
	private SQLiteDatabase sqllitedatabase;
	SmsDatabaseAdapter dbAdapter;
	ListView mListView;
	public ArrayList<CreditData> mCreditData;
	Spinner mSpinner;
	TextView mNodataTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCreditData = new ArrayList<CreditData>();
		setContentView(R.layout.creditmain);
		try {
			db = new DbUtils(this.getFilesDir(), "CreditCardDb.db");
			try {
				db.createDatabaseIfNotExists(this.getApplicationContext());
			} catch (IOException e) {

			}
			sqllitedatabase = db.getStaticDb();
			dbAdapter = new SmsDatabaseAdapter(sqllitedatabase);
			mListView = (ListView) findViewById(R.id.creditlistview);
			mNodataTextView=(TextView)findViewById(R.id.nodatatextview);
		/*	mSummaryButton = (Button) findViewById(R.id.summarybutton);
			mSpinner = (Spinner) findViewById(R.id.myspinner);*/
			/*mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// Toast.makeText(CreditCardMain.this, arg2+"",
					// Toast.LENGTH_SHORT).show();
					int month = arg2 + 1;
					String mMonth = month + "";
					if (month < 10) {
						mMonth = "0" + mMonth;
					}
					setData(mMonth);
				}

				

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});*/

		setData("");
			mSummaryButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					try {
						if (!isOnline()) {
							Toast.makeText(CreditCardMain.this, "No Internet",
									Toast.LENGTH_SHORT).show();
						} else {

							Cursor cur = dbAdapter.getAllData();
							String MasterBody = "";

							for (cur.moveToFirst(); !cur.isAfterLast(); cur
									.moveToNext()) {

								MasterBody = MasterBody
										+ cur.getString(cur
												.getColumnIndexOrThrow("date"))
										+ "\t\t";
								MasterBody = MasterBody
										+ cur.getString(cur
												.getColumnIndexOrThrow("time"))
										+ "\t\t";
								MasterBody = MasterBody
										+ cur.getString(cur
												.getColumnIndexOrThrow("amount"))
										+ "\t\t";
								MasterBody = MasterBody
										+ cur.getString(cur
												.getColumnIndexOrThrow("description"))
										+ "\t\t";
								MasterBody = MasterBody
										+ cur.getString(cur
												.getColumnIndexOrThrow("bankname"))
										+ "\t\t\n";

							}

							GMailSender sender = new GMailSender(
									"testandroid1990@gmail.com",
									"testandroid11");
							try {
								sender.sendMail("Credit card summary",
										MasterBody, "tracker@android.com",
										"zac@startupvillage.in");
								Toast.makeText(CreditCardMain.this,
										"Data sent", Toast.LENGTH_SHORT).show();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out
										.println("Exception occured in tracker");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void setData(String mMonth) {
		Cursor cursor = dbAdapter.getAllData();
		if(cursor.getCount()==0)
		{
			mNodataTextView.setVisibility(View.VISIBLE);
		}
		else
		{
			mNodataTextView.setVisibility(View.INVISIBLE);
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
				.moveToNext()) {
			CreditData cdata = new CreditData();

			cdata.date = cursor.getString(cursor
					.getColumnIndexOrThrow("date"));
			cdata.time = cursor.getString(cursor
					.getColumnIndexOrThrow("time"));
			cdata.amount = cursor.getString(cursor
					.getColumnIndexOrThrow("amount"));
			cdata.description = cursor.getString(cursor
					.getColumnIndexOrThrow("description"));
			cdata.bankname = cursor.getString(cursor
					.getColumnIndexOrThrow("bankname"));
			mCreditData.add(cdata);

		}
		mListView.setAdapter(new MyAdapter(CreditCardMain.this));
		
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

}
