package com.kizio.suncorptest.sync

import android.accounts.AbstractAccountAuthenticator
import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * A [Service] that handles the authentication operation.
 *
 * @author Graeme Sutherland
 * @since 28/01/2018
 */
class AuthenticatorService : Service() {

	/**
	 * The [AbstractAccountAuthenticator] used to authenticate the user to the remote webservice.
	 */
	private var mAuthenticator : AbstractAccountAuthenticator? = null

	override fun onCreate() {
		super.onCreate()

		if (mAuthenticator == null) {
			mAuthenticator = StubAuthenticator(this)
		}
	}

	/**
	 * Invoked when the [Service] is bound.
	 *
	 * @param intent The [Intent] containing the binding request
	 * @return The [IBinder] from the [AbstractAccountAuthenticator] used to bind the [Service]
	 */
	override fun onBind(intent: Intent?): IBinder? {
		return mAuthenticator?.iBinder
	}
}
