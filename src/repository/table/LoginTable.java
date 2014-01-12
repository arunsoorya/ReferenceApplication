/*@(#)LoginTable.java
 */
package repository.table;

import repository.DatabaseConstants;
import repository.DatabaseConstants.LoginFields;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author arunkumar.s,arunkumar.s@experionglobal.com <br>
 *         May 9, 2013 <br>
 *         Package:- <b>{com.topgolf.mobile.datastore</b> <br>
 *         Project:- <b>{SplashScreen</b>
 *         <p>
 */
public class LoginTable {
	/** The Constant DATABASE_CREATE. */
	private static final String DATABASE_CREATE = "create table "
			+ DatabaseConstants.LOGIN_TABLE + "(" + LoginFields._ID + " integer primary key autoincrement, "
			+ LoginFields.MEMBER_EMAIL + " text, " + LoginFields.MEMBER_ID
			+ " text, " + LoginFields.PASSWORD + " text, "
			+ LoginFields.KEEP_LOGGED_IN + " text, " + LoginFields.AUTH_KEY + " text);";

	/**
	 * On create. Create new RegisteredUser table.
	 * 
	 * @param database the database
	 */
	public final void onCreate(final SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	/**
	 * On upgrade. Drop existing table and create new table.
	 * 
	 * @param database the database
	 * @param oldVersion the old version
	 * @param newVersion the new version
	 */
	public final void onUpgrade(final SQLiteDatabase database,
			final int oldVersion, final int newVersion) {
		Log.w(LoginTable.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS "
				+ DatabaseConstants.LOGIN_TABLE);
		onCreate(database);
	}
}
