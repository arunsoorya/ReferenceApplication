package constants;

/*@(#)ViewConstants.java}
 */

import android.content.Context;

public class ViewConstants {

	/** The constant isToBackground. */
	public static boolean isToBackground;
	/** The previous context. */
	public static Context previousContext;
	/** The splash time - determines how long to show splash screen. */
	public static final int splashTime = 3000;// 3 seconds
	/** The constant FINISH. */
	public static final String FINISH = "FINISH";
	/** The constant GPS_DIALOG_DISMISSED. */
	public static final String GPS_DIALOG_DISMISSED = "GPS_DIALOG_DISMISSED";
	/** The constant INTENT_TYPE_BROADCAST. */
	public static final String INTENT_TYPE_BROADCAST = "text/plain";
	/** The constant Request code gps. */
	public static final int REQUEST_CODE_GPS = 7;
	/** The constant TAG_OBJECT_INDEX. */
	public static final int TAG_OBJECT_INDEX = 1;
	/** The constant DISABLED_INDEX. */
	public static final int DISABLED_INDEX = 0;
	/** The constant ENABLED_INDEX. */
	public static final int ENABLED_INDEX = 1;
	/** The constant GPS_DALOG_SHOWED. */
	public static int GPS_DALOG_SHOWED = 1;
	/** The constant GPS_DALOG_NOT_SHOWED. */
	public static int GPS_DALOG_NOT_SHOWED = 0;
	/** The constant ANIMATION_DURATION. */
	public static final int ANIMATION_DURATION = 1500;
	/** The location wait time. */
	public static final int locationWaitTime = 5000;
	public static final String GPS = "gps";
	/** The NETWORK constant. */
	public static final String NETWORK = "network";
	/** The string ERROR_NO_NETWORK. */
	public static String ERROR_NO_NETWORK = "erNNW";

	public static String ERROR_AUTHENTICATION_FAILED = "duplicateLoginError";
	/** The constant STATUS_OK. */
	public static final int STATUS_OK = 200;

	/** The string FACEBOOK_URL_HEADER. */
	public static String FACEBOOK_URL_HEADER = "http://";
	/** The string TWITTER_URL_HEADER. */
	public static String TWITTER_URL_HEADER = "http://twitter.com/";
	/** The string MD5_ALGORITHM. */
	public static String MD5_ALGORITHM = "MD5";
	/** The string CONSTANT_ZERO. */
	public static String CONSTANT_ZERO = "0";
	
	
	/** The string INTERNAL_SERVER_ERROR. */
	public static String INTERNAL_SERVER_ERROR = "erISE";
	/** The string EXCEPTION_ERROR_CODE. */
	public static String EXCEPTION_ERROR_CODE = "erEXPTN";
	/** The message enum constants. */
	public enum MessageEnum {
		SUCCESS, ERROR, NO_NETWORK
	}

	public enum TypeFaceEnum {
		LIGHT("Light"), MEDIUM("Medium"), REGULAR("Regular"), BOLD("Bold");
		private String value;

		private TypeFaceEnum(String val) {
			value = val;
		}

		@Override
		public String toString() {

			return this.value;
		}
	}

	/** Email regular expression. */
	public static final String regEmail = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
			+ "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
			+ "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
			+ "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
			+ "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

	/** The DateFormat enum constant. */
	public enum DateFormats {
		DDMMMYYY_HYPHEN("dd-MMM-yyyy"), DDMMYYYY_HYPHEN("dd-MM-yyyy"), YYYYMMDD_HYPHEN(
				"yyyy-MM-dd"), DMY_SLASH("%m/%d/%Y"), MMMDY_SPACE("MMM dd yyyy"), MMMD_SPACE(
				"MMM dd"), YYYY("yyyy"), HHMMA_COLON_SPACE("hh:mm a"), YMD_HYPHEN_HMS(
				"%Y-%m-%d %H:%M:%S"), YMD_HYPHEN("yyyy-MM-dd"), MDY_SLASH(
				"MM/dd/yyyy"), MDY_HYPHEN("MM-dd-yyyy");

		private String value;

		private DateFormats(String val) {
			value = val;
		}

		@Override
		public String toString() {

			return this.value;
		}
	};
}
