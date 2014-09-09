package com.example.creditcardtracker;

import java.io.IOException;

import com.example.Utils.DBUtils.DbUtils;
import com.example.Utils.DBUtils.SmsDatabaseAdapter;
import com.sv.creditcardtracker.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
	String mSms = "";
	String mBankName = "";

	@Override
	public void onReceive(Context ctx, Intent intent) { // Receiving sms

		String mNumber = "";
		String amount = "";
		Boolean flag = false;
		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get("pdus");
		SmsMessage[] messages = new SmsMessage[pdus.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			Log.v("SMSFun", "Body: " + messages[i].getDisplayMessageBody());
			Log.v("SMSFun",
					"Address: " + messages[i].getDisplayOriginatingAddress());
			mNumber = messages[i].getDisplayOriginatingAddress();
			mSms = mSms + messages[i].getDisplayMessageBody();
		}
		String mLowerSms = null;
		mLowerSms = String.valueOf(mSms);
		mLowerSms = mLowerSms.toLowerCase();
		mNumber = mNumber.toLowerCase();

		if (mNumber.contains("idbi")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "IDBI";
			}
		} else if (mNumber.contains("citi")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "Citi";
			}
		} else if (mNumber.contains("fed")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "Fed";
			}
		} else if (mNumber.contains("hdfc")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "HDFC";
			}
		} else if (mNumber.contains("hsbc")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "HSBC";
			}
		} else if (mNumber.contains("icici")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "ICICI";
			}
		} else if (mNumber.contains("sbi")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "SBI";
			}
		} else if (mNumber.contains("-")) {
			if (mLowerSms.contains("credit card")) {
				amount = takeAmount(mSms);
				flag = true;
				mBankName = "Others";
			}
		}

		if (flag) {

			Intent i = new Intent(ctx, MainActivity.class);
			i.putExtra("amount", amount);
			i.putExtra("bankname", mBankName);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(i);

		}

	}

	public static String takeAmount(String msg) {

		int i = 0;
		msg = msg.replace(" ", "");
		msg = msg.replace(",", "");

		// System.out.println(msg);
		int ln = 0;
		if (msg.contains("Rs")) {
			ln = msg.indexOf("Rs");
			for (i = ln + 2; i < msg.length(); i++) {
				char c = msg.charAt(i);
				if ((

				(int) c < 48 || (int) c > 57

				) && (int) c != 46) {
					break;
				}
			}
			String result = msg.substring(ln + 2, i);
			if (result.startsWith(".")) {
				result=result.replaceFirst(".", "");
			}

			return result;
		} else {
			ln = msg.indexOf("INR");
			for (i = ln + 3; i < msg.length(); i++) {
				char c = msg.charAt(i);
				if ((

				(int) c < 48 || (int) c > 57

				) && (int) c != 46) {
					break;
				}
			}
			String result = msg.substring(ln + 3, i);
			if (result.startsWith(".")) {
				result=result.replaceFirst(".", "");
			}
			return result;
		}

	}

}