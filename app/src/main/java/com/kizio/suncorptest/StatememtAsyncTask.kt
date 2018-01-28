package com.kizio.suncorptest

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.widget.GridView
import com.kizio.suncorptest.data.StatementContract
import com.kizio.suncorptest.data.StatementItem
import com.kizio.suncorptest.sync.StatementDownloader
import com.kizio.suncorptest.ui.StatementAdapter

/**
 * Test code to see if I can update the database.
 *
 * @author Graeme Sutherland
 * @since 28/01/2018
 */
class StatememtAsyncTask (aContext: Context, aGrid: GridView) : AsyncTask<Void, Void, Array<StatementItem>>() {

	private val context: Context = aContext.applicationContext

	private val gridView = aGrid

	/**
	 * Downloads the values from the Internet.
	 *
	 * @param voids Takes no parameters
	 * @return The downloaded files
	 */
	override fun doInBackground(vararg voids: Void): Array<StatementItem>? {
		val items = StatementDownloader.downloadStatements()

		if (items != null && items.isNotEmpty()) {
			val resolver: ContentResolver = context.contentResolver
			val values: Array<ContentValues?> = arrayOfNulls<ContentValues?>(items.size)

			for ((index, item) in items.withIndex()) {
				val value = ContentValues(4)
				values[index] = value

				// Neither the BigDecimal nor Data class can be stored directly in the database. So
				// they need to be converted into primitives, and then reconstructed when read.
				value.put(StatementContract.ID_KEY, item.id)
				value.put(StatementContract.DATE_KEY, item.date.time)
				value.put(StatementContract.DESCRIPTION_KEY, item.description)
				value.put(StatementContract.AMOUNT_KEY, item.amount.toString())
			}

			// Clear the table before insertion.
			resolver.delete(StatementContract.URI, null, null)

			resolver.bulkInsert(StatementContract.URI, values)
		}

		return items
	}

	override fun onPostExecute(result: Array<StatementItem>?) {
		super.onPostExecute(result)

		if (result != null) {
			val adapter: StatementAdapter = StatementAdapter(context, result)

			gridView.adapter = adapter
		}
	}
}
