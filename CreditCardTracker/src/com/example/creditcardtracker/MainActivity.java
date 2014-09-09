package com.example.creditcardtracker;

import java.io.IOException;

import com.example.Utils.DBUtils.DbUtils;
import com.example.Utils.DBUtils.SmsDatabaseAdapter;
import com.sv.creditcardtracker.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView mAmountTextView;
	DbUtils db;
	private SQLiteDatabase sqllitedatabase;
	SmsDatabaseAdapter dbAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DbUtils(this.getFilesDir(), "CreditCardDb.db");
		try {
			db.createDatabaseIfNotExists(this.getApplicationContext());
		} catch (IOException e) {

		}
		sqllitedatabase = db.getStaticDb();
		dbAdapter = new SmsDatabaseAdapter(sqllitedatabase);
		Intent i=getIntent();
		String amount= i.getStringExtra("amount");
		String bankname=i.getStringExtra("bankname");
		
		
		ConfirmDialog d=new ConfirmDialog(this, amount,bankname);
		
		d.show();
		
		
							
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
