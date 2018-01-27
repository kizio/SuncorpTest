package com.kizio.suncorptest.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Holds the values for a line in an account statement.
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 */
class StatementItem () : Comparable<StatementItem> {

	/**
	 * Constants for parsing the JSON returned from the server.
	 */
	private companion object {

		/**
		 * [String] containing the ID field in the JSON from the server.
		 */
		private const val ID : String = "id"

		/**
		 * [String] containing the description field in the JSON from the server.
		 */
		private const val DESCRIPTION : String = "description"

		/**
		 * [String] containing the amount spent field in the JSON from the server.
		 */
		private const val AMOUNT : String = "amount"

		/**
		 * [String] containing the effective date field in the JSON from the server.
		 */
		private const val DATE : String = "effectiveDate"
	}

	/**
	 * A [SimpleDateFormat] object used to convert a date object into a human readable format.
	 */
	private val DISPLAY_FORMAT = SimpleDateFormat("MM/dd/yyyy", Locale.UK)

	/**
	 * The ID for the statement item.
	 */
	@SerializedName(ID)
	var id : Int = 0

	/**
	 * A description of what was purchased.
	 */
	@SerializedName(DESCRIPTION)
	var description : String = ""

	/**
	 * Floating point values aren't ideal for storing currencies due to rounding errors, so the
	 * amount of money is a [BigDecimal].
	 */
	@SerializedName(AMOUNT)
	var amount : BigDecimal = BigDecimal(0)

	/**
	 * The initialisation block for the default constructor.
	 */
	@SerializedName(DATE)
	var date : Date = Date(0)

	/**
	 * Formats the date into a [String] with the form DD/MM/YYYY.
	 *
	 * @return The formatted [Date] as a [String]
	 */
	fun getFormattedDateString() : String {
		return DISPLAY_FORMAT.format(date)
	}

	/**
	 * Compares this [StatementItem] to another. This is based on the embedded [Date] object.
	 *
	 * @return 0 if they're equal, negative if it's less than the [other], positive if greater
	 */
	override fun compareTo(other: StatementItem): Int {
		return date.compareTo(other.date)
	}
}
