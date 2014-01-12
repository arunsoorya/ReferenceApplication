/*@(#)NavigationElement.java}
 */
package navigationcontroller;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class NavigationElement {
	/** The key. */
	private String key;

	/** The class object. */
	private Class<? extends Activity> classObject;
	/** The fragmentObject object. */
	private Class<? extends Fragment> fragmentObject;

	/** The config. */
	private NavigationConfigBase config;

	/**
	 * Instantiates a new nav element.
	 * @param key
	 *            the key
	 * @param config
	 *            the config
	 */
	public NavigationElement(String key, NavigationConfigBase config) {
		this.key = key;
		this.config = config;
	}

	/**
	 * Gets the name.
	 * @return the name
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 * @param key
	 *            the new key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the class object.
	 * @return the class object
	 */
	public Class<? extends Activity> getClassObject() {
		return classObject;
	}

	/**
	 * Gets the fragment object.
	 * @return the fragment object
	 */
	public Class<? extends Fragment> getFragmentObject() {
		return fragmentObject;
	}

	/**
	 * Go to.
	 * @param classObject
	 *            the class object
	 */
	public void goTo(Class<? extends Activity> classObject) {
		this.classObject = classObject;
		config.add(this);
	}
	/**
	 * Go to.
	 * @param fragmentObject
	 *            the fragment Object
	 */
	public void goToFragment(Class<? extends Fragment> fragmentObject) {
		this.fragmentObject = fragmentObject;
		config.add(this);
	}

	/**
	 * Equals.
	 * @param o
	 *            the o
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		NavigationElement that = (NavigationElement) o;

		if (!classObject.equals(that.classObject)) {
			return false;
		}

		return key.equals(that.key);
	}

	/**
	 * Hash code.
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		int result = key.hashCode();
		result = 31 * result + classObject.hashCode();
		return result;
	}
}
