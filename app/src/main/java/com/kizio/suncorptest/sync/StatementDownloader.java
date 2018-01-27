package com.kizio.suncorptest.sync;

import com.google.gson.Gson;
import com.kizio.suncorptest.data.StatementItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is based on a piece of code I used to download some JSON from a Twitter API. I'm using
 * it to save myself an hour or two of putting something identical together.
 * <p>
 * I tried to convert it to Kotlin, but the tools messed it up, so it's staying in Java.
 * </p>
 *
 * @author Graeme Sutherland
 * @since 26/01/2018
 */
public enum StatementDownloader {;
	/** URL {@link String} pointing to the test server endpoint. */
	private static final String URL =
			"https://private-ddc1b2-transactions14.apiary-mock.com/transactions"; //$NON-NLS-1$

	/**
	 * Downloads the statements from the server.
	 *
	 * @return A {@link StatementItem} array containing the server's response
	 */
	public static StatementItem[] downloadStatements() {
		HttpURLConnection connection = null;
		StatementItem[] result = null;

		try
		{
			final URL url = new URL(URL);
			connection = (HttpURLConnection) url.openConnection ();

			connection.connect ();

			result = handleResponse (connection);
		}

		catch (final Exception e)
		{
			// I need to implement logging here.
			e.printStackTrace ();
		}

		finally
		{
			if (connection != null)
			{
				connection.disconnect ();
			}
		}

		return result;
	}

	/**
	 * Reads the response from the HTTP connection, and parses it into an array of
	 * {@link StatementItem} objects. This contains the account's statement information.
	 *
	 * @param connection The {@link HttpURLConnection} to the server
	 * @return A {@link StatementItem} array containing the server's response
	 * @throws IOException If there is an IO error
	 */
	private static StatementItem[] handleResponse (final HttpURLConnection connection)
			throws IOException
	{
		final InputStream stream = connection.getInputStream();
		final BufferedReader reader = new BufferedReader (new InputStreamReader(stream));

		try
		{
			final Gson gson = new Gson();

			return gson.fromJson(reader, StatementItem[].class);
		}

		finally
		{
			// I think that I need to wrap this in a try / catch block, so that if closing the
			// reader fails, the stream will also close.
			try {
				reader.close();
			} finally {
				stream.close();
			}
		}
	}
}
