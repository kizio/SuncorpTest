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
class StatementHelper(context : Context) : SQLiteOpenHelper(context, STATEMENT_DATABASE, null, VERSION) {

	/**
	 * Holds constant values for the [SQLiteOpenHelper].
	 * <p>
	 * I wouldn't hardcode these in production code! :)
	 * </p>
	 */
	companion object {

		/**
		 * The filename of the file used to save the database in.
		 */
		private const val STATEMENT_DATABASE: String = "accountStatement"

		/**
		 * The database table holding the account statement.
		 */
		const val STATEMENT_TABLE : String = "statement"

		/**
		 * The ID column in the database.
		 */
		const val ID_KEY : String = "id"

		/**
		 * The description column in the database.
		 */
		private const val DESCRIPTION_KEY : String = "description"

		/**
		 * The amount column in the database.
		 */
		private const val AMOUNT_KEY : String = "amount"

		/**
		 * The date column in the database.
		 */
		private const val DATE_KEY : String = "effectiveDate"

		/**
		 * Canned SQL code to create the statement table.
		 */
		private const val CREATE_STATEMENT_TABLE : String = """
				CREATE TABLE $STATEMENT_TABLE (
					$ID_KEY INTEGER PRIMARY KEY,
					$DESCRIPTION_KEY TEXT NOT NULL,
					$AMOUNT_KEY REAL NOT NULL,
					$DATE_KEY INTEGER NOT NULL)"""

		/**
		 * Canned SQL code to drop the statement table.
		 */
		private const val DROP_STATEMENT_TABLE : String = "DROP TABLE IF EXISTS " + STATEMENT_TABLE

		/**
		 * The version of the database. It's always 1.
		 */
		private const val VERSION : Int = 1
	}

	/**
	 * Called when the database is created for the first time.
	 *
	 * @param db The [SQLiteDatabase] in which the database is being created
	 */
	override fun onCreate(db: SQLiteDatabase?) {
		if (db != null) {
			db.execSQL(CREATE_STATEMENT_TABLE);
		}
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
			db.execSQL(DROP_STATEMENT_TABLE)

			onCreate(db)
		}
	}
}