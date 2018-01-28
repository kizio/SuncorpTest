package com.kizio.suncorptest.ui

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * @author Graeme Sutherland
 * @since 28/01/2018
 */
class StatementCursorAdapter(context: Context?, cursor: Cursor?, flags: Int) : CursorAdapter(context, cursor, flags) {
	/**
	 * Bind an existing view to the data pointed to by cursor.
	 *
	 * @param view Existing view, returned earlier by newView
	 * @param context Interface to application's global information
	 * @param cursor The cursor from which to get the data. The cursor is already
	 * moved to the correct position.
	 */
	override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	/**
	 * Makes a new view to hold the data pointed to by cursor.
	 *
	 * @param context The [Context] the list is being displayed in
	 * @param cursor The cursor from which to get the data
	 * @param parent The parent to which the new view is attached to
	 * @return The newly created view.
	 */
	override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
		return TextView(context)
	}
}
