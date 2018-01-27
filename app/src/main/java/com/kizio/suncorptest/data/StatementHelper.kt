package com.kizio.suncorptest.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Implementation of [SQLiteOpenHelper] that handles opening a table for an account statement.
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 *
 * @param context The [Context] the helper object is running in
 */
class StatementHelper(context : Context) : SQLiteOpenHelper(context,
		StatementContract.STATEMENT_DATABASE, null, StatementContract.VERSION) {

	/**
	 * Called when the database is created for the first time.
	 *
	 * @param db The [SQLiteDatabase] in which the database is being created
	 */
	override fun onCreate(db: SQLiteDatabase?) {
		db?.execSQL(StatementContract.CREATE_STATEMENT_TABLE)
	}

	/**
	 * Invoked when the database is to be upgraded. This simply drops the old table, and creates a
	 * new one.
	 * <p>
	 * There would probably be a better implementation in production code, which would attempt to
	 * keep the existing table.
	 * </p>
	 *
	 * @param db The [SQLiteDatabase] database to upgrade
	 * @param oldVersion The old database version number
	 * @param newVersion The new database version number
	 */
	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		if (db != null && oldVersion != newVersion) {
			db.execSQL(StatementContract.DROP_STATEMENT_TABLE)

			onCreate(db)
		}
	}
}