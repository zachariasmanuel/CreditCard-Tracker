package com.example.Utils.DBUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbUtils {
	public static String DB_PATH;
	private String DB_NAME;
	File dbDir;
	private SQLiteDatabase dataBase;
	public DbUtils(File fileDirectory, String sqliteFileName) {

		this.DB_NAME = sqliteFileName;
		dbDir = fileDirectory;
	}

	public void createDatabaseIfNotExists(Context context) throws IOException {
		boolean createDb = false;
		
		File dbFile = new File(dbDir.getAbsolutePath() + "/" + DB_NAME);
		DB_PATH = dbFile.getAbsolutePath();

		if (!dbDir.exists()) {
			dbDir.mkdir();
			createDb = true;
		} else if (!dbFile.exists()) {
			createDb = true;
		} else {
			boolean doUpgrade = false;

			if (doUpgrade) {
				dbFile.delete();
				createDb = true;
			}
		}

		if (createDb) {
			InputStream myInput = context.getAssets().open(DB_NAME);
			OutputStream myOutput = new FileOutputStream(dbFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			myOutput.flush();
			myOutput.close();
			myInput.close();
		}
	}

	public SQLiteDatabase getStaticDb() {
		return dataBase = SQLiteDatabase.openDatabase(DB_PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
	}
	
	public void closeDataBase(){
		if(dataBase!=null && dataBase.isOpen()){
			dataBase.close();
		}
	}

}
