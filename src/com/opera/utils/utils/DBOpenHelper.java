package com.opera.utils.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	private static DBOpenHelper mInstance = null;

	public static final String DATABASE_NAME = "database.db";

	public static final String TBL_SCREEN_MSG = "tbl_screen_msg";

	public static final String TBL_SCREEN_MSG_CREATE = "create table "
			+ TBL_SCREEN_MSG + " (id integer primary key autoincrement,"
			+ "imgname text not null," + "appalias text not null)";

	public static final String TBL_MSG_STATUS = "tbl_msg_status";

	// 0 notifacation 1 screen
	public static final String TBL_MSG_STATUS_CREATE = "create table "
			+ TBL_MSG_STATUS
			+ " (id integer primary key autoincrement,msg_type integer,msg_display_date integer)";

	public static final String TBL_INSTALL_STATUS = "tbl_install_status";

	public static final String TBL_INSTALL_STATUS_CREATE = "create table "
			+ TBL_INSTALL_STATUS
			+ " (id integer primary key autoincrement,apppakname text,install_count integer)";

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public static DBOpenHelper getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new DBOpenHelper(ctx.getApplicationContext());
		}
		return mInstance;
	}

	private DBOpenHelper(Context ctx) {
		super(ctx, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("DROP TABLE IF EXISTS " + TBL_SCREEN_MSG);

		db.execSQL(TBL_SCREEN_MSG_CREATE);

		db.execSQL("DROP TABLE IF EXISTS " + TBL_MSG_STATUS);

		db.execSQL(TBL_MSG_STATUS_CREATE);

		db.execSQL("DROP TABLE IF EXISTS " + TBL_INSTALL_STATUS);

		db.execSQL(TBL_INSTALL_STATUS_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TBL_SCREEN_MSG);
		onCreate(db);
	}

}
