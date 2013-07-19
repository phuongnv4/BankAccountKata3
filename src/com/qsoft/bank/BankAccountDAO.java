package com.qsoft.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author quynhlt
 * 
 */
public class BankAccountDAO {
	private SQLiteDatabase db;

	public BankAccountDAO(Context context, String name) {
		DBHelper openHelper = new DBHelper(context, name);
		this.db = openHelper.getWritableDatabase();
	}

	public long insert(BankAccountDTO bankAccountDTO) {
		long result = 0;
		BankAccountDTO bankAccount = get(bankAccountDTO.getAccountNumber());
		boolean notExisted = bankAccount == null ? true : false;
		Log.e("notExisted 0=", notExisted + "");
		if (notExisted) {
			Log.e("notExisted 1=", notExisted + "");

			ContentValues values = new ContentValues();
			values.put(DBHelper.ACCOUNT_NUMBER,
					bankAccountDTO.getAccountNumber());
			values.put(DBHelper.BALANCE, bankAccountDTO.getBalance());
			values.put(DBHelper.OPEN_TIME_STAMP, bankAccountDTO.getTimeStamp());
			result = db.insert(DBHelper.TABLE_ACCOUNT, null, values);
		}
		return result;
	}

	public long update(BankAccountDTO bankAccountDTO) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.ACCOUNT_NUMBER, bankAccountDTO.getAccountNumber());
		values.put(DBHelper.BALANCE, bankAccountDTO.getBalance());
		values.put(DBHelper.OPEN_TIME_STAMP, bankAccountDTO.getTimeStamp());
		return db.update(DBHelper.TABLE_ACCOUNT, values,
				DBHelper.ACCOUNT_NUMBER + " = ?",
				new String[] { bankAccountDTO.getAccountNumber() });
	}

	public int getRecordSize() {
		String countQuery = "SELECT  * FROM " + DBHelper.TABLE_ACCOUNT;
		Cursor cursor = db.rawQuery(countQuery, null);
		return cursor.getCount();
	}

	public BankAccountDTO get(String accountNumber) {
		BankAccountDTO bankAccount = null;
		Cursor cursor = db.query(DBHelper.TABLE_ACCOUNT, new String[] {
				DBHelper.KEY_ID, DBHelper.ACCOUNT_NUMBER, DBHelper.BALANCE,
				DBHelper.OPEN_TIME_STAMP }, DBHelper.ACCOUNT_NUMBER + "=?",
				new String[] { accountNumber }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToNext();
			bankAccount = new BankAccountDTO(cursor.getString(cursor
					.getColumnIndex(DBHelper.ACCOUNT_NUMBER)));
			bankAccount.setBalance(cursor.getInt(cursor
					.getColumnIndex(DBHelper.BALANCE)));
			bankAccount.setTimeStamp(cursor.getLong(cursor
					.getColumnIndex(DBHelper.OPEN_TIME_STAMP)));
		}
		return bankAccount;
	}
}