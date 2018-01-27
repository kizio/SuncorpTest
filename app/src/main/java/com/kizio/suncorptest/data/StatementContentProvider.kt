package com.kizio.suncorptest.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils

/**
 * A [ContentProvider] that manages an account's statement data on the device.
 * <p>
 * I probably don't need one of these, but it makes sense since I'm going down the SyncAdapter path.
 * Besides, I couldn't remember what they did in the interview, so it gives me an excuse to revise
 * them. The last time I wrote one was when I was working for Samsung.
 * </p>
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 */
class StatementContentProvider : ContentProvider() {

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
		 * MIME type for a single row of the statement table.
		 */
		const val MIME_STATEMENT_ITEM: String = VND_CURSOR + "item/vnd." + URI + "." + StatementHelper.STATEMENT_TABLE

		/**
		 * MIME type for a single row of the statement table.
		 */
		const val MIME_STATEMENT_LIST: String = VND_CURSOR + "dir/vnd." + URI + "." + StatementHelper.STATEMENT_TABLE

		/**
		 * The code for a single row in the statement table.
		 */
		private const val STATEMENT_ITEM: Int = 1

		/**
		 * The code for multiple rows in the statement table.
		 */
		private const val STATEMENT_LIST: Int = 2
	}

	/**
	 * [UriMatcher] that matches a [Uri] to a database table.
	 */
	private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

	/**
	 * [StatementHelper] used to create, manage, and access the statement database.
	 */
	private var mHelper: StatementHelper? = null

	/**
	 * Initialisation code.
	 */
	init {
		/**
		 * URI that matches multiple rows in the table.
		 */
		uriMatcher.addURI(URI, StatementHelper.STATEMENT_TABLE, STATEMENT_ITEM)

		/**
		 * URI that matches a single row in the table, where the # is a wildcard for its number.
		 */
		uriMatcher.addURI(URI, StatementHelper.STATEMENT_TABLE + "/#", STATEMENT_LIST)
	}

	/**
	 * Inserts values into the table.
	 *
	 * @param uri The content [URI] for the insertion request
	 * @param values A set of column_name/value pairs to add to the database
	 * @return The [URI] for the newly inserted item
	 */
	override fun insert(uri: Uri?, values: ContentValues?): Uri? {
		val insertUri: Uri?

		if (uriMatcher.match(uri) == STATEMENT_LIST) {
			val db = getDatabase()
			val id = db.insert(StatementHelper.STATEMENT_TABLE, null, values)
			insertUri = ContentUris.withAppendedId(uri, id)

			context.contentResolver.notifyChange(insertUri, null)
		} else {
			insertUri = null
		}

		return insertUri
	}

	/**
	 * Performs a query on the Content Provider.
	 *
	 * @param uri The [Uri] to query
	 * @param projection The list of columns that are being queried, null if all are used
	 * @param selection The selection query for rows in the data
	 * @param selectionArgs Arguments to select rows
	 * @param sortOrder The sort order to
	 * @return A [Cursor] corresponding to the data from the query
	 */
	override fun query(uri: Uri?, projection: Array<out String>?, selection: String?,
					   selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
		var cursor: Cursor? = null

		if (uri != null) {
			when (uriMatcher.match(uri)) {
				STATEMENT_ITEM -> {
					cursor = performQuery(projection, getWhere(uri, selection), selectionArgs, sortOrder)
				}

				STATEMENT_LIST -> {
					cursor = performQuery(projection, selection, selectionArgs, sortOrder)
				}
			}
		}

		return cursor
	}

	/**
	 * Invoked when the content provider is created.
	 *
	 * @return True if the provider was successfully loaded, false otherwise
	 */
	override fun onCreate(): Boolean {
		val result : Boolean

		if (context != null) {
			mHelper = StatementHelper(context)
			result = true
		} else {
			result = false
		}

		return result
	}

	/**
	 * Updates the specified values in the table.
	 *
	 * @param uri The [Uri] to query
	 * @param values The [ContentValues] to update the table with
	 * @param selection An optional [String] to filter the rows to update
	 * @return The number of rows affected
	 */
	override fun update(uri: Uri?, values: ContentValues?, selection: String?,
						selectionArgs: Array<out String>?): Int {
		var updated = 0

		if (uri != null) {
			val db = getDatabase()

			when (uriMatcher.match(uri)) {
				STATEMENT_ITEM -> {
					updated = db.update(StatementHelper.STATEMENT_TABLE, values,
							getWhere(uri, selection), selectionArgs)
				}

				STATEMENT_LIST -> {
					updated = db.update(StatementHelper.STATEMENT_TABLE, values, selection,
							selectionArgs)
				}
			}
		}

		return updated	}

	/**
	 * Deletes one or more rows from the database.
	 *
	 * @param uri The full URI to query, including a row ID (if a specific record is requested).
	 * @param selection An optional restriction to apply to rows when deleting.
	 * @return The number of rows deleted
	 */
	override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
		var deleted = 0

		if (uri != null) {
			val db = getDatabase()

			when (uriMatcher.match(uri)) {
				STATEMENT_ITEM -> {
					deleted = db.delete(StatementHelper.STATEMENT_TABLE, getWhere(uri, selection),
							selectionArgs)
				}

				STATEMENT_LIST -> {
					deleted = db.delete(StatementHelper.STATEMENT_TABLE, selection, selectionArgs)
				}
			}
		}

		return deleted
	}

	/**
	 * Gets the MIME type for the specified [URI].
	 *
	 * @param uri The [URI] to query
	 * @return A MIME type [String], or null if there's no type
	 */
	override fun getType(uri: Uri?): String? {
		return when(uriMatcher.match(uri)) {
			STATEMENT_ITEM -> MIME_STATEMENT_ITEM
			STATEMENT_LIST -> MIME_STATEMENT_LIST
			else -> null
		}
	}

	/**
	 * Helper method to get a writeable [SQLiteDatabase].
	 *
	 * @param isReadOnly Flag indicating if the database read only
	 * @return A [SQLiteDatabase]
	 */
	private fun getDatabase(isReadOnly: Boolean = false): SQLiteDatabase {
		val helper: StatementHelper? = mHelper

		// In theory this shouldn't happen. In production code I'd have a better piece of code to
		// handle this corner case.
		if (helper == null) {
			 throw IllegalStateException("App attempted to access DB before it was created.")
		} else {
			return if (isReadOnly) {
				helper.readableDatabase
			} else {
				helper.writableDatabase
			}
		}
	}

	/**
	 * Builds up a select statement from a [Uri] for a single item, and a selection [String].
	 *
	 * @param uri The [Uri] to the table row
	 * @param selection The optional selection parameters [String] passed into the query
	 */
	private fun getWhere(uri: Uri, selection: String?): String {
		val where: String = StatementHelper.ID_KEY + " = " + uri.lastPathSegment

		return if (!TextUtils.isEmpty(selection)) {
			where + " AND " + selection
		} else {
			where
		}
	}

	/**
	 * Performs a query on the database.
	 *
	 * @param projection The list of columns that are being queried, null if all are used
	 * @param selection The selection query for rows in the data
	 * @param selectionArgs Arguments to select rows
	 * @param sortOrder The sort order to
	 * @return A [Cursor] corresponding to the data from the query
	 */
	private fun performQuery(projection: Array<out String>?, selection: String?,
							 selectionArgs: Array<out String>?, sortOrder: String?) : Cursor {
		val builder = SQLiteQueryBuilder()

		builder.tables = StatementHelper.STATEMENT_TABLE

		return builder.query(
				getDatabase(true),
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder)
	}
}