/*@(#)LoginRepository.java
 */
package repository.datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Login;
import repository.DatabaseConstants;
import repository.DatabaseConstants.LoginFields;
import repository.SQLiteRepositoryBase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;

public class LoginRepository extends SQLiteRepositoryBase<Login> {

	/** The Constant DB_TABLE. */
	private static final String DB_TABLE = DatabaseConstants.LOGIN_TABLE;

	/**
	 * Instantiates a new Login repository.
	 * @param helper the helper
	 */
	@Inject
	public LoginRepository(SQLiteOpenHelper helper) {
		super(helper);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Gets all Logins.
	 * @return the list of Login
	 */
	@Override
	public List<Login> getAll() {
		try {
			List<Login> list = null;
			Cursor cursor = this.getDb().query(DB_TABLE, null, null, null, null,
					null, null);

			if (cursor.moveToFirst()) {
				list = new ArrayList<Login>();
				do {
					list.add(getLoginDetailsFromCursor(cursor));
				} while (cursor.moveToNext());
			}
			cursor.close();
			return list;
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository getAll() " + e);
		}
		return null;
	}

	/**
	 * Gets all keys as a map.
	 * @return keyMap the map of keys
	 */
	@Override
	public Map<String, Object> getAllKeyMap() {
		Map<String, Object> keyMap;
		Cursor cursor = this.getDb().query(DB_TABLE, new String[] {
            LoginFields.MEMBER_ID + ""
				}, null, null, null, null, null);

		keyMap = new HashMap<String, Object>();

		try {
			if (cursor.moveToFirst()) {
				do {
					String LoginKey = cursor.getString(0);
//					String LoginKey = cursor.getString(LoginFields.LoginID.ordinal());
					keyMap.put(LoginKey, LoginKey);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cursor.close();
		return keyMap;
	}

	/**
	 * Gets the Login.
	 * @param id
	 *            the key
	 * @return the Login
	 */
	@Override
	public Login get(String id) {
		try {
			Login Login = null;
			Cursor cursor = this.getDb().query(DB_TABLE, null
					, LoginFields.MEMBER_ID + "='" + id + "'", null, null, null, null);

				if (cursor.moveToFirst()) {
					Login = getLoginDetailsFromCursor(cursor);
				} else {
					Log.d("No user is found with this id", " " + id);
//					throw new IllegalArgumentException(
//							"No Login is found with this LoginId " + id);
				}

				cursor.close();
				return Login;
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository get() " + e);
		}
		return null;
	}

	/**
	 * Adds the Login.
	 * @param Login
	 *            the Login
	 * @return the long
	 */
	@Override
	public long add(Login Login) {
		try {
			ContentValues values = createContentValues(Login);
			return getDb().insert(DB_TABLE, null, values);
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository add() " + e);
		}
		return 0;
	}

	/**
	 * Update the LoginKey.
	 * @param Login
	 *            the Login
	 * @return true, if successful
	 */
	@Override
	public boolean update(Login login) {
		try {
			ContentValues values = createContentValues(login);

			return getDb().update(DB_TABLE, values,
					LoginFields.MEMBER_ID + "='" + login.getMemberEmail() + "'", null) > 0;
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository update() " + e);
		}
		return false;
	}

	/**
	 * Delete the user.
	 * @param login the user login credentials
	 * @return true, if successful
	 */
	@Override
	public boolean delete(Login login) {
		try {
			return this.getDb().delete(DB_TABLE,
					LoginFields.MEMBER_ID + "='" + login.getMemberEmail() + "'", null) > 0;
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository delete() " + e);
		}
		return false;
	}

	/**
	 * Delete all the user.
	 */
	@Override
	public void deleteAll() {
		try {
			this.getDb().delete(DB_TABLE, null, null);
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository deleteAll() " + e);
		}
	}

	/**
	 * get the total record count.
	 */
	@Override
	public int getTotalRecordCount() {
		String sqlQuery = "SELECT COUNT(*) FROM "
				+ DB_TABLE;
		Cursor cursor = this.getDb().rawQuery(sqlQuery, null);
		int count = 0;
		if (cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}

	/**
	 * Creates the content values.
	 * @param Login the Login
	 * @return the content values
	 */
	private ContentValues createContentValues(Login login) {
		try {
			ContentValues values = new ContentValues();

			Map<String, Object> valueMap = createMap(login);

			for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
				if (entry.getValue() != null) {
					values.put(entry.getKey(), entry.getValue().toString());
				}
			}
			return values;
		} catch (Exception e) {
			Log.d("Exception", " LoginRepository createContentValues() " + e);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * This is the method for createMap.
	 * </p>
	 * @param Login the Login
	 * @return the map
	 */
	private Map<String, Object> createMap(Login login) {
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put(LoginFields.MEMBER_EMAIL.toString(), login.getMemberEmail());
		return valueMap;
	}

	private Login getLoginDetailsFromCursor(Cursor cursor) {
		Login login = new Login();
		login.setMemberEmail(cursor.getString(LoginFields.MEMBER_EMAIL.ordinal()));
		return login;
	}
}
