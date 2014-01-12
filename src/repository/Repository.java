/*@(#)Repository.java}
 */
package repository;

import java.util.List;
import java.util.Map;

public interface Repository<TBusinessObject> {
	/**
	 * Open the repository.
	 * @return the repository
	 */
	Repository<TBusinessObject> open();

	/**
	 * Close the repository.
	 */
	void close();

	/**
	 * Gets all objects.
	 * @return the all objects
	 */
	List<TBusinessObject> getAll();

	/**
	 * Gets the single object in the case
	 *  of table that meant to hold only one row.
	 * @return the object
	 */
	TBusinessObject get();

	/**
	 * Gets the {@link String value for the given field}.
	 * @param field
	 *            the database column name
	 * @return the value for the given column
	 */
	String getValueForTheField(String field);

	/**
	 * Gets all keys as a map.
	 * @return keyMap the map of keys
	 */
	Map<String, Object> getAllKeyMap();

	/**
	 * Gets all keys as a map.
	 * @return keyMap the map of keys
	 */
	Map<String, TBusinessObject> getAllMap();

	/**
	 * Gets the object for the given id.
	 * @param id
	 *            the id
	 * @return the t business object
	 */
	TBusinessObject get(String id);

	/**
	 * Gets the object for the given object.
	 * @param object
	 *            the object
	 * @return the t business object
	 */
	TBusinessObject get(TBusinessObject object);

	/**
	 * Gets all object for the given object properties.
	 * @param object
	 *            the object
	 * @return the t business object
	 */
	List<TBusinessObject> getAllForMultipleCondition(TBusinessObject object);

	/**
	 * Gets the list of AppointmentDTO.
	 * @param field
	 *            the field
	 * @param value
	 *             the value
	 * @return the list of appointmentDTO
	 */
	List<TBusinessObject> getAllWithConditionForField(String field, String value);
	/**
	 * Gets the list of AppointmentDTO.
	 * @param field
	 *            the field
	 * @param value
	 *             the value
	 * @return the list of appointmentDTO
	 */
	List<TBusinessObject> getAllWithConditionForField(String id);

	/**
	 * Adds the given object.
	 * @param emp
	 *            the emp
	 * @return the long
	 */
	long add(TBusinessObject emp);

	/**
	 * <p>
	 * This is the method for add the user.
	 * </p>
	 * @param field the field
	 * @param value the value
	 * @return long the long value
	 */
	long add(final String field, final Object value);

	/**
	 * Update the given object.
	 * @param emp
	 *            the emp
	 * @return true, if successful
	 */
	boolean update(TBusinessObject emp);

	/**
	 * <p>
	 * This is the method for Update the user.
	 * </p>
	 * @param field the field
	 * @param value the value
	 * @return true or false
	 */
	boolean update(final String field, final Object value);

	/**
	 * <p>
	 * This is the method for Update the user.
	 * </p>
	 * @param userKey
	 * @param field
	 * @param value
	 * @return true or false
	 */
	boolean update(final String userKey, final String field, final String value);

	/**
	 * Update the provided field of user property .
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	boolean updateValueForField(String field, String value);

	/**
	 * Delete the given object.
	 * @param emp
	 *            the emp
	 * @return true, if successful
	 */
	boolean delete(TBusinessObject emp);
	/**
	 * The method delete all records.
	 */
	void deleteAll();

	/**
	 * The method get total record count.
	 */
	int getTotalRecordCount();

	/**
	 * The method for getBooleanValueOfField.
	 * @return the boolean true or false
	 */
	Boolean getBooleanValueOfField(String fieldName);

	/**
	 * The method for getStringValueOfField.
	 * @return the string selected value
	 */
	String getStringValueOfField(String fieldName);

//	/**
//	 * Gets value that isGpsDialogShowed.
//	 * @return the boolean isGpsDialogShowed
//	 */
//	Boolean isGpsDialogShowed();
//
//	/**
//	 * Gets value that isTheSameSession.
//	 * @return the boolean isTheSameSession
//	 */
//	Boolean isTheSameSession();
//	/**
//	 * The method for get the selected site id in the location screen.
//	 * @return the string selected site id
//	 */
//	String getSelectedSiteIdInLocationScreen();
}
