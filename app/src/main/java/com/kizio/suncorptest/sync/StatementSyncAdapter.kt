package com.kizio.suncorptest.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.ContentResolver
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import com.kizio.suncorptest.data.StatementItem

/**
 * [AbstractThreadedSyncAdapter] that connects to the test server.
 * <p>
 * The adapter is set up to auto initialise, and not run in parallel. Since it's a piece of demo
 * code, it doesn't need to be tweaked in any way for different contexts.
 * </p>
 *
 * @param context The [Context] that the adapter is being created in
 * @author Graeme Sutherland
 * @since 25/01/2018
 */
class StatementSyncAdapter(context: Context, autoInitialize: Boolean, allowParallelSyncs: Boolean)
	: AbstractThreadedSyncAdapter(context, autoInitialize, allowParallelSyncs) {

	/**
	 * Secondary constructor to maintain backwards compatibility. (Not sure if this is needed.)
	 */
	constructor(context: Context, autoInitialize: Boolean): this(context, autoInitialize, false)

	private val contentResolver: ContentResolver = context.contentResolver

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
		val items: Array<StatementItem>? = StatementDownloader.downloadStatements()

		if (items != null) {
			val resolver: ContentResolver = context.contentResolver
		}
	}
}