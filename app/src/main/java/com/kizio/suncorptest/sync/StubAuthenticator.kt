package com.kizio.suncorptest.sync

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.content.Context
import android.os.Bundle

/**
 * Stubbed out [AbstractAccountAuthenticator]. The endpoint that I'm connecting to doesn't use any
 * authentication, so this exists to keep the sync adapter happy.
 * <p>
 * This is a version of the stub adapter given in the Android tutorial, just written in Kotlin,
 * rather than Java. Code can be found at:
 * </p>
 * <a:href=https://developer.android.com/training/sync-adapters/creating-authenticator.html>
 * https://developer.android.com/training/sync-adapters/creating-authenticator.html</a>
 *
 * @author Graeme Sutherland
 * @since 25/01/2018
 *
 * @param context The [Context] the authenticator is being created in
 */
class StubAuthenticator(context : Context) : AbstractAccountAuthenticator(context) {
	override fun getAuthTokenLabel(authTokenType: String?): String {
		throw UnsupportedOperationException()
	}

	override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle? {
		return null
	}

	override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
		throw UnsupportedOperationException()
	}

	override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle {
		throw UnsupportedOperationException()
	}

	override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle {
		throw UnsupportedOperationException()
	}

	override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle {
		throw UnsupportedOperationException()
	}

	override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle? {
		return null
	}
}