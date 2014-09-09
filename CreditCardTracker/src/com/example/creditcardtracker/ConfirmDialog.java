package com.example.creditcardtracker;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sv.creditcardtracker.R;

public class ConfirmDialog extends Dialog implements
		android.view.View.OnClickListener {
	Button mSubmitButton;
	TextView mMessageTextView;
	EditText descEditText;
	MainActivity mMain;
	String mAmount;
	String mBankName="";

	public ConfirmDialog(Context context, String message, String bankname) {
		super(context);
		mMain=(MainActivity) context;
	//	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.smsdialog);
		
		mMessageTextView = (TextView) findViewById(R.id.messagedittext);
		descEditText=(EditText)findViewById(R.id.descriptiontextview);
		mSubmitButton = (Button) findViewById(R.id.smssubmitbutton);
		mMessageTextView.setText("Rs."+message+" was spent on ");
		mAmount=message;
		mBankName=bankname;
		mSubmitButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onBackPressed() {
		
	}

	public void onClick(View v) {
		mMain.dbAdapter.insertintodetails(mAmount, descEditText.getText().toString(),mBankName);
		mMain.finish();

	}

}
