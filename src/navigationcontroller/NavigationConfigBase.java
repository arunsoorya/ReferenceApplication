/*@(#)NavigationConfigBase.java}
 */
package navigationcontroller;

import java.util.HashMap;
import java.util.Map;

import roboguice.inject.InjectResource;

import com.example.webviewcache.R;

public abstract class NavigationConfigBase {
	/** The map. */
	private Map<String, NavigationElement> map = new HashMap<String, NavigationElement>();

	/** The duplicate key msg. */
	@InjectResource(R.string.duplicate_nav_key)
	private String duplicateKeyMsg;

	/** The nav not found msg. */
	@InjectResource(R.string.nav_not_found)
	private String navNotFoundMsg;

	/**
	 * Configure.
	 */
	public abstract void configure();

	/**
	 * On new key.
	 * 
	 * @param key
	 *            the key
	 * @return the nav element
	 */
	protected NavigationElement on(String key) {
		return new NavigationElement(key, this);
	}

	/**
	 * Gets the class object.
	 * 
	 * @param key
	 *            the key
	 * @return the class object
	 */
	public Class<?> getClassObject(String key) {
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(navNotFoundMsg,
					key));
		}
		NavigationElement navElement = map.get(key);
		return navElement.getClassObject();
	}

	/**
	 * Gets the class object.
	 * 
	 * @param key
	 *            the key
	 * @return the class object
	 */
	public Class<?> getFragmentObject(String key) {
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(navNotFoundMsg,
					key));
		}
		NavigationElement navElement = map.get(key);
		return navElement.getFragmentObject();
	}

	/**
	 * Adds the nav element.
	 * 
	 * @param navElement
	 *            the nav element
	 */
	public void add(NavigationElement navElement) {
		String key = navElement.getKey();
		if (map.containsKey(key)) {
			throw new IllegalArgumentException(String.format(duplicateKeyMsg,
					key));
		}

		map.put(key, navElement);
	}
}
