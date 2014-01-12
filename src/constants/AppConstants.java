/*@(#)AppConstants.java}
 */
package constants;


public class AppConstants {
	/** The constant DATABASE_NAME. */
	public static final String DATABASE_NAME = "topgolf.sqlite";
	/** The Constant DATABASE_VERSION. */
	public static final int DATABASE_VERSION = 2;
	/** The constant DATABASE_NAME. */
	/** The constant GPS_MIN_TIME. */
	public static long GPS_MIN_TIME = 0;// in milliseconds
	/** The constant GPS_MIN_DISTANCE. */
	public static float GPS_MIN_DISTANCE = 0;// in meters

	public static String PLATINUM_MEMBER_ID = "3";
	/** The string BASE_URL. */
	// public static String BASE_URL =
	// "http://staging.experionglobal.com/projects/tg_data_services";
	public static String BASE_URL = "http://58.68.91.118/projects/tg_api/v0.5/";

	/**
	 * 
	 * @author vipin.cb , vipin.cb@experionglobal.com <br>
	 *         May 9, 2013, 4:20:25 PM <br>
	 *         Package:- <b>com.topgolf.mobile.views</b> <br>
	 *         Project:- <b>TopGolfMobile</b>
	 *         <p>
	 */
	public enum ApiEnum {
		MEMBER_LOGIN("account/memberLogin/"), GET_GAMES_LIST(
				"games/getGamesList?"), GET_SITES("sites/getSites?"), GET_SITES_AND_METADATA(
				"sites/getSitesAndMetaData?"), GET_SCORE_DETAILS(
				"ABCDTOPGOLF123"), GET_PROMOS("locations/getPromos?"), GET_NEWS(
				"locations/getNews?"), GET_META_VALUES(
				"sites/getSiteMetaValues?"), GET_APP_CONFIG("app/getAppConfig?"), VERIFY_COUPON_CODE(
				"account/verifyCouponCode?"), ADD_PRIORITY_PASS(
				"account/addPriorityPass"), ADD_CREDIT("account/addCredit/"), GET_PROFILE(
				"account/getMemberDetails?"), GET_TOTAL_BALANCE(
				"account/getTotalBalance?"), MEMBER_STATISTICS(
				"account/memberStatistics?"), GET_PRIORITY_PASSES_COUNT(
				"account/getPriorityPassesCount?"), LOG_OUT("account/logout?"), GET_SCORES_AND_DETAILS(
				"games/getScoresAndDetails?"), LEADERBOARD(
				"leaderboards/getLeaderBoards?"), LEADERBOARD_LOCATION(
				"leaderboards/getLeaderBoardList?"), LEADERBOARD_MEMBER(
				"leaderboards/getMemberPosition?"), GET_NEAREST_SITE(
				"sites/getNearestSite?");

		private String value;

		private ApiEnum(String val) {
			value = val;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
}
