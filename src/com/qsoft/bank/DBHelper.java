package com.qsoft.bank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "bankaccount.sql";
	private static final int VERSION = 1;
	public static final String TABLE_ACCOUNT = "account";
	public static final String KEY_ID = "keyid";
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String BALANCE = "balance";
	public static final String OPEN_TIME_STAMP = "openTimestamp";

	public static final String TABLE_TRANSACTION = "transactions";
	public static final String AMOUNT = "amount";
	public static final String DESCRIPTION = "description";

	private static final String CREATE_TABLE_ACCOUNT = "create table "
			+ TABLE_ACCOUNT + " (" + KEY_ID
			+ " integer primary key autoincrement, " + ACCOUNT_NUMBER
			+ " text not null" + "," + BALANCE + " integer," + OPEN_TIME_STAMP
			+ " long);";

	private static final String CREATE_TABLE_TRANSACTION = "create table "
			+ TABLE_TRANSACTION + " (" + KEY_ID
			+ " integer primary key autoincrement, " + ACCOUNT_NUMBER
			+ " text not null" + "," + AMOUNT + " integer," + OPEN_TIME_STAMP
			+ " long," + DESCRIPTION + " text not null);";

	public DBHelper(Context context, String name) {
		super(context, name, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
		sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,
			int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
	}

}