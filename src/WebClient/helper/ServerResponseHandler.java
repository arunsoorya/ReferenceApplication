package WebClient.helper;
/*@(#)ServerResponseHandler.java}
 */


/**
 * @author vipin.cb , vipin.cb@experionglobal.com
 * <br>May 28, 2013, 10:52:14 AM
 * <br>Package:- <b>com.topgolf.mobile.services</b>
 * <br>Project:- <b>TopGolfMobile</b>
 * <p>
 */
public interface ServerResponseHandler {
	/**
	 * <p>The method that receives the response of post.<p>
	 * @param serverResponse the serverResponse
	 */
	void onServerResponse(String serverResponse);
}
