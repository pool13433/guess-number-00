package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	public static final String TABLE_GUESS = "guess";
	public static final String COL_ID = "_id";
	public static final String COL_PLAYER = "gPlayName";
	public static final String COL_LEVEL = "gLevelPlay";
	public static final String COL_DATE_CREATE = "mDateCreate";
	public static final String COL_SCORE = "gScore";
	public static final String COL_WINNER = "gWinner";
	public static final String COL_LOSE = "gLose";

	private static final String DATABASE_NAME = "mydb";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = 
			"CREATE TABLE "+ TABLE_GUESS
			+ " ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_PLAYER + " VARCHAR(50) ,"			
			+ COL_LEVEL + " INTEGER(1) ,"  // 0,1,2
			+ COL_SCORE + " INTEGER(10) ,"
			+ COL_DATE_CREATE + " DATE ,"
			+ COL_WINNER + " INTEGER(5) ,"			
			+ COL_LOSE + " INTEGER(5) );";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GUESS);
		onCreate(db);
	}

}
