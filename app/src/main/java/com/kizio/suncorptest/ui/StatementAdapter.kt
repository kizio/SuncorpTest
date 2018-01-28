package com.kizio.suncorptest.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kizio.suncorptest.R
import com.kizio.suncorptest.data.StatementItem

/**
 * Maps the database data onto a Grid View
 *
 * @author Graeme Sutherland
 * @since 28/01/2018
 */
class StatementAdapter(context: Context, items: Array<StatementItem>): BaseAdapter() {

	/**
	 * The [Context] used to create views.
	 */
	private val mContext = context

	/**
	 * The [StatementItem] objects to display.
	 */
	private val mItems = items

	/**
	 * The number of columns that the grid displays.
	 */
	private val mColumns = context.resources.getInteger(R.integer.columns)

	/**
	 * Creates a simple [TextView] to display the value in.
	 *
	 * @param position The position of the item in the grid
	 * @param convertView The [View] to reuse, if possible
	 * @param parent The parent [View] that the newly created view will be attached to
	 * @return A [View] to display the item in
	 */
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView as? TextView ?: TextView(mContext)

		view.text = getItem(position)

		return view
	}

	/**
	 * Gets the data item associated with the specified position in the data set.
	 * <p>
	 * Each item represents three columns, so it needs to be mapped onto a 2D value.
	 * </p>
	 *
	 * @param position Position of the item in the list view
	 * @return The data at the specified position.
	 */
	override fun getItem(position: Int): String {
		val index = getIndex(position)
		val item = mItems[index]
		val modulus = if (mColumns != 0) {
			position % mColumns
		} else {
			0
		}

		// Since the data can be of various types, there needs to be a conversion to a String to
		// display.
		return when(modulus) {
			0 -> {
				item.getFormattedDateString()
			}

			1 -> {
				item.amount.toString()
			}

			else -> {
				item.description
			}
		}
	}

	/**
	 * Gets the row id associated with the specified position in the list.
	 *
	 * @param position The position of the item within the adapter's data set whose row id we want
	 * @return The id of the item at the specified position
	 */
	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	/**
	 * How many items are in the data set represented by this Adapter.
	 *
	 * @return The number of items
	 */
	override fun getCount(): Int {
		return mItems.size * mColumns
	}

	/**
	 * Gets the position of the item. Each corresponds to three columns, so this function maps the
	 * list position onto a particular item.
	 */
	private fun getIndex(position: Int): Int {
		return if (position != 0) {
			position / mColumns
 		} else {
			0
		}
	}
}
