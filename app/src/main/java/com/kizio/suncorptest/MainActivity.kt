package com.kizio.suncorptest

import android.accounts.Account
import android.accounts.AccountManager
import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Main {@link Activity} for the Suncorp Test Application.
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 */
class MainActivity : AppCompatActivity() {

	private var account: Account? = null

	/**
	 * Invoked when the {@link Activity} is created.
	 * <p>
	 * Note that Kotlin parameters are defined as a <code>val</code> by default, and hence cannot be
	 * reassigned.
	 * </p>
	 *
	 * @param savedState A {@link Bundle} containing saved state from a previous activity instance
	 */
	override fun onCreate(savedState: Bundle?) {
		super.onCreate(savedState)

		setContentView(R.layout.activity_main)

		initialiseAccount()

		initialiseGrid()

		StatememtAsyncTask(this, findViewById(R.id.grid_view)).execute()
	}

	/**
	 * Initialises the account used by the app.
	 */
	private fun initialiseAccount() {
		if (account == null) {
			val manager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
			val authority = getString(R.string.authority)
			account = Account(getString(R.string.account_name), getString(R.string.account_type))

			manager.addAccountExplicitly(account, null, null)

			ContentResolver.setSyncAutomatically(account, authority, true)
			ContentResolver.setIsSyncable(account, authority, 1)
		}

		val syncExtras = Bundle()

		syncExtras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true)
		syncExtras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true)

		ContentResolver.requestSync(account, getString(R.string.authority), syncExtras)
	}


	private fun initialiseGrid() {

	}
}
