package com.kizio.suncorptest.data

import android.database.Cursor

/**
 * Provides the contract that is used to access the Statement table in the database.
 *
 * @author Graeme Sutherland
 * @since 28/01/2018
 */
class StatementContract {
	/**
	 * Contains the constants used to access the database.
	 */
	companion object {
		/**
		 * Constant representing the URI to the database.
		 */
		const val URI: String = "com.kizio.suncorptest.provider"

		/**
		 * Relative path in the URI to get to the statement table.
		 */

		/**
		 * The start of a MIME type for a [Cursor] on Android.
		 */
		private const val VND_CURSOR: String = "vnd.android.cursor."

		/**
		 * The database table holding the account statement.
		 */
		const val STATEMENT_TABLE : String = "statement"

		/**
		 * MIME type for a single row of the statement table.
		 */
		const val MIME_STATEMENT_ITEM: String = VND_CURSOR + "item/vnd." + URI + "." + STATEMENT_TABLE

		/**
		 * MIME type for a single row of the statement table.
		 */
		const val MIME_STATEMENT_LIST: String = VND_CURSOR + "dir/vnd." + URI + "." + STATEMENT_TABLE

		/**
		 * The code for a single row in the statement table.
		 */
		const val STATEMENT_ITEM: Int = 1

		/**
		 * The code for multiple rows in the statement table.
		 */
		const val STATEMENT_LIST: Int = 2


		/**
		 * The filename of the file used to save the database in.
		 */
		const val STATEMENT_DATABASE: String = "accountStatement"

		/**
		 * The ID column in the database.
		 */
		const val ID_KEY : String = "id"

		/**
		 * The description column in the database.
		 */
		const val DESCRIPTION_KEY : String = "description"

		/**
		 * The amount column in the database.
		 */
		const val AMOUNT_KEY : String = "amount"

		/**
		 * The date column in the database.
		 */
		const val DATE_KEY : String = "effectiveDate"

		/**
		 * Canned SQL code to create the statement table.
		 */
		const val CREATE_STATEMENT_TABLE : String = """
				CREATE TABLE $STATEMENT_TABLE (
					$ID_KEY INTEGER PRIMARY KEY,
					$DESCRIPTION_KEY TEXT NOT NULL,
					$AMOUNT_KEY REAL NOT NULL,
					$DATE_KEY DATETIME NOT NULL)"""

		/**
		 * Canned SQL code to drop the statement table.
		 */
		const val DROP_STATEMENT_TABLE : String = "DROP TABLE IF EXISTS " + STATEMENT_TABLE

		/**
		 * The version of the database. It's always 1.
		 */
		const val VERSION : Int = 1

	}
}