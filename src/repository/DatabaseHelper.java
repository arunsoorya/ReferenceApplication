/*@(#)DatabaseHelper.java}
 */
package repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;

import constants.AppConstants;


public class DatabaseHelper extends SQLiteOpenHelper {
	/** The context. */
	private Context context;

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = AppConstants.DATABASE_NAME;

	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = AppConstants.DATABASE_VERSION;

	/** The userTable. */
//	private final LoginTable loginTable;

	/**
	 * 
	 * Constructor for DatabaseHelper.
	 * @param context the Context
	 */
	@Inject
	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * On create.
	 * @param database the database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
//		loginTable.onCreate(database);
	}

	/**
	 * On upgrade.
	 * @param database the database
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//		loginTable.onUpgrade(database, oldVersion, newVersion);
	}


	/**
	 * 
	 * <p>
	 * This is the method for checkDataBase.
	 * </p>
	 * @return true or false
	 */
	public boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase("/data/data/"
					+ context.getPackageName() + "/databases/"
					+ AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
			checkDB.close();
		} catch (SQLiteException e) {
			Log.d("SQLiteException ", "database doesn't exist yet. " + e);
		}
		return checkDB != null ? true : false;
	}

	/**
	 * 
	 * <p>
	 * This is the method for copyDataBase.
	 * </p>
	 * @throws IOException the IOException
	 */
	public void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream inputStream = context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = getDBPath(DATABASE_NAME) + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream outPutStream = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outPutStream.write(buffer, 0, length);
		}

		// Close the streams
		outPutStream.flush();
		outPutStream.close();
		inputStream.close();
	}

	/**
	 * 
	 * <p>
	 * This is the method for getDBPath.
	 * </p>
	 * @param dbName the db name
	 * @return the db path
	 */
	private String getDBPath(String dbName) {
		// return context.getDatabasePath(dbName).getAbsolutePath();
		return "/data/data/" + context.getApplicationContext().getPackageName()
				+ "/databases/";
	}
}
