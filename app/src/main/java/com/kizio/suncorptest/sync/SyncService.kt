package com.kizio.suncorptest.sync

import android.accounts.AbstractAccountAuthenticator
import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * A [Service] that handles the synchronisation operation.
 *
 * @author Graeme Sutherland
 * @since 25/01/2018
 */
class SyncService : Service() {
	/**
	 * The [StatementSyncAdapter] used to synchronise the contents of the app's database.
	 */
	val mAdapter: StatementSyncAdapter = StatementSyncAdapter(this)

	/**
	 * The [AbstractAccountAuthenticator] used to authenticate the user to the remote webservice.
	 */
	private val mAuthenticator : AbstractAccountAuthenticator = StubAuthenticator(this)

	/**
	 * Invoked when the [Service] is bound.
	 *
	 * @param intent The [Intent] containing the binding request
	 * @return The [IBinder] from the [AbstractAccountAuthenticator] used to bind the [Service]
	 */
	override fun onBind(intent: Intent?): IBinder {
		return mAuthenticator.iBinder
	}
}