package com.kizio.suncorptest.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import com.kizio.suncorptest.data.StatementContract

/**
 * [AbstractThreadedSyncAdapter] that connects to the test server.
 * <p>
 * The adapter is set up to auto initialise, and not run in parallel. Since it's a piece of demo
 * code, it doesn't need to be tweaked in any way for different contexts.
 * </p>
 *
 * @author Graeme Sutherland
 * @since 25/01/2018
 *
 * @param context The [Context] that the adapter is being created in
 * @param autoInitialize Flag indicating whether or not the adapter should be initialised
 * @param allowParallelSyncs Flag indicating whether synchronisation can be run in parallel
 */
class StatementSyncAdapter(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean)
	: AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

	/**
	 * Secondary constructor to maintain backwards compatibility.
	 */
	// constructor(context: Context, autoInitialize: Boolean): this(context, autoInitialize, false)

	/**
	 * Invoked when a sync operation is to be performed.
	 *
	 * @param account The [Account] that should be synchronised. Can be null
	 * @param extras A [Bundle] containing extra configuration settings. Can be null
	 * @param authority A [String] containing the authority for the request
	 * @param provider The [ContentProviderClient] used by the authority's Content Provider
	 * @param syncResult A [SyncResult] containing provider specific settings
	 */
	override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?,
							   provider: ContentProviderClient?, syncResult: SyncResult?) {
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
	}
}
