package com.qsoft.bank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class to manage database creation and version management.
 * 
 * You create a subclass implementing onCreate(SQLiteDatabase),
 * onUpgrade(SQLiteDatabase, int, int) and optionally onOpen(SQLiteDatabase),
 * and this class takes care of opening the database if it exists, creating it
 * if it does not, and upgrading it as necessary. Transactions are used to make
 * sure the database is always in a sensible state.
 * 
 * This class makes it easy for ContentProvider implementations to defer opening
 * and upgrading the database until first use, to avoid blocking application
 * startup with long-running database upgrades.
 * 
 * For an example, see the NotePadProvider class in the NotePad sample
 * application, in the samples/ directory of the SDK.
 * 
 */
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

	private static final String CREATE_TABLE_ACCOUNT = "create table " + TABLE_ACCOUNT + " (" + KEY_ID + " integer primary key autoincrement, "
			+ ACCOUNT_NUMBER + " text not null" + "," + BALANCE + " integer," + OPEN_TIME_STAMP + " long);";

	private static final String CREATE_TABLE_TRANSACTION = "create table " + TABLE_TRANSACTION + " (" + KEY_ID
			+ " integer primary key autoincrement, " + ACCOUNT_NUMBER + " text not null" + "," + AMOUNT + " integer," + OPEN_TIME_STAMP + " long,"
			+ DESCRIPTION + " text not null);";

	/**
	 * @param context
	 *            Context to use to open or create the database
	 * @param name
	 *            Name of the database file, or null for an in-memory database
	 */
	public DBHelper(Context context, String name) {
		super(context, name, null, VERSION);
	}

	/**
	 * @param sqLiteDatabase
	 *            Called when the database is created for the first time. This
	 *            is where the creation of tables and the initial population of
	 *            the tables should happen.
	 */
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
		sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION);
	}

	/**
	 * Called when the database needs to be upgraded. The implementation should
	 * use this method to drop tables, add tables, or do anything else it needs
	 * to upgrade to the new schema version.
	 * 
	 * The SQLite ALTER TABLE documentation can be found here. If you add new
	 * columns you can use ALTER TABLE to insert them into a live table. If you
	 * rename or remove columns you can use ALTER TABLE to rename the old table,
	 * then create the new table and then populate the new table with the
	 * contents of the old table.
	 * 
	 * This method executes within a transaction. If an exception is thrown, all
	 * changes will automatically be rolled back.
	 * 
	 * @param sqLiteDatabase
	 *            The database.
	 * @param oldVersion
	 *            The old database version.
	 * @param newVersion
	 *            The new database version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
	}

}