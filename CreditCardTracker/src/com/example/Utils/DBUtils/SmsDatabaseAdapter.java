package com.example.Utils.DBUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SmsDatabaseAdapter {

	SQLiteDatabase _sqlDatabase;

	public SmsDatabaseAdapter(SQLiteDatabase sqldb) {
		_sqlDatabase = sqldb;
	}

	public int getNumberOfRows_Result(String sql, String[] selectionArgs) {
		return _sqlDatabase.rawQuery(sql, selectionArgs).getCount();

	}

	public Cursor get_ResultCursor_for_Query(String sql, String[] selectionArgs) {
		return _sqlDatabase.rawQuery(sql, selectionArgs);

	}

	public Cursor get_all_data_from_messages() {
		return _sqlDatabase.rawQuery("select * from messages", null);
	}

	public void Delete_message_thread(String number) {
		System.out.println("The number = " + number);
		// _sqlDatabase.rawQuery("delete from messages where address = '"+number+"'",
		// null);
		_sqlDatabase.delete("messages", "address = '" + number + "'", null);

	}

	public Cursor Delete_message(int id) {
		return _sqlDatabase.rawQuery("delete from messages where id = " + id
				+ "", null);
	}

	public String get_Name(String number) {
		Cursor cur = _sqlDatabase.rawQuery(
				"select * from privatenumbers where number like \'" + number
						+ "\'", null);
		cur.moveToFirst();
		String name = cur.getString(cur.getColumnIndexOrThrow("name"));
		cur.close();
		return name;
	}

	public boolean addToPrivateNumber(ContentValues c) {
		if (_sqlDatabase.insert("privatenumbers", null, c) == -1)
			return false;
		else
			return true;
	}

	public boolean insertToMessages(String s1, String s2, Boolean direction) {

		ContentValues cv = new ContentValues();
		cv.put("address", s1);
		cv.put("message", s2);
		cv.put("time",
				android.text.format.DateFormat.format("h:mm:aa ddMMM",
						new java.util.Date()).toString());

		if (direction) {
			cv.put("status", "unread");
			cv.put("direction", "in");
		} else {
			cv.put("status", "read");
			cv.put("direction", "out");
		}
		System.out.println(System.currentTimeMillis());
		if (_sqlDatabase.insert("messages", null, cv) == -1)
			return false;
		else
			return true;

	}

	public boolean insertintodetails(String amount, String description, String mBankName) {
		ContentValues cv = new ContentValues();
		cv.put("amount", amount);
		cv.put("description", description);
		cv.put("date",
				android.text.format.DateFormat.format("dd-MM-yyyy",
						new java.util.Date()).toString());
		cv.put("time",
				android.text.format.DateFormat.format("h:mm:aa",
						new java.util.Date()).toString());
		cv.put("bankname", mBankName);
		if (_sqlDatabase.insert("details", null, cv) == -1)
			return false;
		else
			return true;
	}
	
	public Cursor getAllData()
	{
		return _sqlDatabase.rawQuery("select * from details", null);
	}
	public Cursor getDataForAMonth(String month)
	{
		return _sqlDatabase.rawQuery("select * from details where date like '%___"+month+"_____%'", null);
	}

	public void updateMessagesToRead(String number) {
		ContentValues cv = new ContentValues();
		cv.put("status", "read");

		int l = _sqlDatabase.update("messages", cv, "address LIKE \'" + number
				+ "\'", null);
		System.out.println(l);
	}

	public Cursor getAllPrivateNumbers() {
		return _sqlDatabase.rawQuery("select * from privatenumbers", null);
	}

	public void Delete_privatenumbers(int id) {

		_sqlDatabase.delete("privatenumbers", "id = " + id, null);
	}

	public boolean checkWhetherExist(String sql, String[] selectionArgs) {
		int numberOfresult = getNumberOfRows_Result(sql, selectionArgs);

		if (numberOfresult > 0) {
			return true;
		} else
			return false;
	}

	public ContentValues getContentValues(String key[], String value[]) {
		ContentValues cValues = new ContentValues();
		if (key.length == value.length) {

			for (int i = 0; i < key.length; i++) {
				cValues.put(key[i], value[i]);
			}

		}

		return cValues;
	}

}