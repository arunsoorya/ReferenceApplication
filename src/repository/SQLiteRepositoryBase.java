/*@(#)SQLiteRepositoryBase.java}
 */
package repository;

import java.util.List;
import java.util.Map;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteRepositoryBase<TBusinessObject> implements
Repository<TBusinessObject> {
	/** The database helper. */
	private final SQLiteOpenHelper helper;

	/** The db. */
	private SQLiteDatabase db;

	/**
	 * Instantiates a new sQ lite repository base.
	 * @param helper
	 *            the helper
	 */
	public SQLiteRepositoryBase(SQLiteOpenHelper helper) {
		this.helper = helper;
	}

	/**
	 * Open the database.
	 * @return the repository
	 */
	public Repository<TBusinessObject> open() {
		db = helper.getWritableDatabase();
		return this;
	}

	/**
	 * Close the database.
	 */
	public void close() {
		db.close();
	}

	/**
	 * Gets the db object.
	 * @return the db
	 */
	protected SQLiteDatabase getDb() {
		return db;
	}

	/**
	 * Gets all objects.
	 * @return the all objects
	 */
	@Override
	public List<TBusinessObject> getAll() {
		return null;
	}

	/**
	 * Gets the single object in the case
	 *  of table that meant to hold only one row.
	 * @return the object
	 */
	@Override
	public TBusinessObject get() {
		return null;
	}

	/**
	 * Gets the {@link String value for the given field}.
	 * @param field
	 *            the database column name
	 * @return the value for the given column
	 */
	@Override
	public String getValueForTheField(String field) {
		return "";
	}

	/**
	 * Gets all keys as a map.
	 * @return keyMap the map of keys
	 */
	@Override
	public Map<String, Object> getAllKeyMap() {
		return null;
	}

	/**
	 * Gets all keys as a map.
	 * @return keyMap the map of keys
	 */
	@Override
	public Map<String, TBusinessObject> getAllMap() {
		return null;
	}

	/**
	 * Gets the object for the given id.
	 * @param id
	 *            the id
	 * @return the t business object
	 */
	@Override
	public TBusinessObject get(String id) {
		return null;
	}

	/**
	 * Gets the object for the given object.
	 * @param object
	 *            the object
	 * @return the t business object
	 */
	@Override
	public TBusinessObject get(TBusinessObject object) {
		return null;
	}

	/**
	 * Gets the list of TBusinessObject.
	 * @param object
	 *            the TBusinessObject
	 * @return the list of TBusinessObject
	 */
	@Override
	public List<TBusinessObject> getAllForMultipleCondition(TBusinessObject object) {
		return null;
	}

	/**
	 * Gets the list of TBusinessObject.
	 * @param field
	 *            the field
	 * @param value
	 *             the value
	 * @return the list of TBusinessObject
	 */
	@Override
	public List<TBusinessObject> getAllWithConditionForField(String field, String value) {
		return null;
	}
	/**
	 * Gets the list of TBusinessObject.
	 * @param field
	 *            the field
	 * @param value
	 *             the value
	 * @return the list of TBusinessObject
	 */
	@Override
	public List<TBusinessObject> getAllWithConditionForField(String id) {
		return null;
	}

	/**
	 * Adds the given object.
	 * @param emp
	 *            the emp
	 * @return the long
	 */
	@Override
	public long add(TBusinessObject emp) {
		return 0;
	}

	/**
	 * <p>
	 * This is the method for add the user.
	 * </p>
	 * @param field the field
	 * @param value the value
	 * @return true or false
	 */
	@Override
	public long add(final String field, final Object value) {
		return 0;
	}

	/**
	 * Update the given object.
	 * @param emp
	 *            the emp
	 * @return true, if successful
	 */
	@Override
	public boolean update(TBusinessObject emp) {
		return false;
	}

	/**
	 * <p>
	 * This is the method for Update the user.
	 * </p>
	 * @param field the field
	 * @param value the value
	 * @return true or false
	 */
	@Override
	public boolean update(final String field, final Object value) {
		return false;
	}

	/**
	 * <p>
	 * This is the method for Update the user.
	 * </p>
	 * @param userKey the user
	 * @param field the field
	 * @param value the vlaue
	 * @return true or false
	 */
	@Override
	public boolean update(final String userKey, final String field, final String value) {
		return false;
	}

	/**
	 * Update the provided field of user property .
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	@Override
	public boolean updateValueForField(String field, String value) {
		return false;
	}

	/**
	 * Delete the given object.
	 * @param emp
	 *            the emp
	 * @return true, if successful
	 */
	@Override
	public boolean delete(TBusinessObject emp) {
		return false;
	}

	/**
	 * The method delete all records.
	 */
	@Override
	public void deleteAll() {
	}

	/**
	 * The method get total record count.
	 */
	@Override
	public int getTotalRecordCount() {
		return 0;
	}

	/**
	 * The method for getBooleanValueOfField.
	 * @return the boolean true or false
	 */
	@Override
	public Boolean getBooleanValueOfField(String fieldName) {
		return false;
	}

	/**
	 * The method for getStringValueOfField.
	 * @return the string selected value
	 */
	@Override
	public String getStringValueOfField(String fieldName) {
		return "";
	}

//	/**
//	 * Gets value that isGpsDialogShowed.
//	 * @return the boolean isGpsDialogShowed
//	 */
//	@Override
//	public Boolean isGpsDialogShowed() {
//		return false;
//	}
//
//	/**
//	 * Gets value that isTheSameSession.
//	 * @return the boolean isTheSameSession
//	 */
//	@Override
//	public Boolean isTheSameSession() {
//		return false;
//	}
//	
//	/**
//	 * The method for get the selected site id in the location screen.
//	 * @return the string selected site id
//	 */
//	@Override
//	public String getSelectedSiteIdInLocationScreen() {
//		return "";
//	}

}