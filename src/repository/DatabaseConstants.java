/*@(#)DatabaseConstants.java}
 */
package repository;


public class DatabaseConstants {
	public static String LOGIN_TABLE = "Login";

	public enum LoginFields {
		_ID("_ID"), MEMBER_EMAIL("member_email"), MEMBER_ID("member_id"), PASSWORD(
				"password"), KEEP_LOGGED_IN("keep_logged_in"), AUTH_KEY(
				"auth_key");
		private String value;

		private LoginFields(String val) {
			value = val;
		}

		@Override
		public String toString() {

			return this.value;
		}
	};

}
