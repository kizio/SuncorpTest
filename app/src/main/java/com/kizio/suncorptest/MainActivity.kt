package com.kizio.suncorptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Main {@link Activity} for the Suncorp Test Application.
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 */
class MainActivity : AppCompatActivity() {

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
	}
}
