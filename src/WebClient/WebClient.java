package WebClient;
/*@(#)WebClient.java
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import network.CheckNetworkUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import WebClient.helper.ServerResponseHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.webviewcache.R;

import constants.ViewConstants;
import constants.ViewConstants.MessageEnum;

/**
 * @author arunkumar.s,arunkumar.s@experionglobal.com <br>
 *         May 8, 2013 <br>
 *         Package:- <b>{com.topgolf.mobile.webclient</b> <br>
 *         Project:- <b>{SplashScreen</b>
 *         <p>
 */
public class WebClient extends AsyncTask<String, String, String> {
	/** The context. */
	private Context context;
	/** The interface ServerResponseHandler. */
	private ServerResponseHandler serverResponseHandler;
	/** The checkNetworkUtil. */
	private CheckNetworkUtil checkNetworkUtil;

	/**
	 * 
	 * Constructor for WebClient.
	 * @param context the context
	 * @param serverResponseHandler the serverResponseHandler
	 */
	public WebClient(Context context,
			ServerResponseHandler serverResponseHandler) {
		this.context = context;
		this.serverResponseHandler = serverResponseHandler;
		checkNetworkUtil = new CheckNetworkUtil(context);
	}

	/**
	 * 
	 * <p>
	 * This is the method for execute the api.
	 * </p>
	 * @param jsonString the request json string with parameter
	 * @param apiString the api string with url
	 * @return flag - true or false to indicate whether the fuction call was
	 *         success or failure
	 */
	public MessageEnum executeApi(String jsonString, String apiString) {
		try {
			CheckNetworkUtil checkNetworkUtil = new CheckNetworkUtil(context);
			if (checkNetworkUtil.isNetAvailable()) {
				String[] strArray = new String[2];
				strArray[0] = jsonString;
				strArray[1] = apiString;
				this.execute(strArray);
				return MessageEnum.SUCCESS;
			} else {
				return MessageEnum.NO_NETWORK;
			}
		} catch (Exception e) {
			Log.d("Exception", " WebClient executeApi() " + e);
			return MessageEnum.ERROR;
		}
	}

	/**
	 * 
	 * <p>
	 * This is the method for executePost.
	 * </p>
	 * @param jsonRequestString the json request string
	 * @param urlString the url string
	 * @return builder the string
	 */
	public String executePost(String jsonRequestString, String urlString) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlString);

		// ArrayList<NameValuePair> nam = new ArrayList<NameValuePair>();
		// nam.add(new BasicNameValuePair("data", jsonRequestString));
		// nam.add(new BasicNameValuePair("", jsonRequestString));
		Log.d("requesting URL------", urlString);
		Log.d("sending data------", jsonRequestString.toString());
		try {

			// UrlEncodedFormEntity form;
			// form = new UrlEncodedFormEntity(nam);
			// form.setContentEncoding(HTTP.UTF_8);
			// httpPost.setEntity(new UrlEncodedFormEntity(nam, "UTF-8"));
			// httpPost.setEntity(form);

			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setEntity(new StringEntity(jsonRequestString, "UTF-8"));

			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == ViewConstants.STATUS_OK) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
//				builder.append(getErrorJsonString(
//						ViewConstants.INTERNAL_SERVER_ERROR,
//						context.getString(R.string.internal_server_error)));
				Log.e("Post was not successfull", "Status code is "
						+ statusCode);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
//			builder.append(getErrorJsonString(
//					ViewConstants.EXCEPTION_ERROR_CODE,
//					context.getString(R.string.exception_text)));
		} catch (IOException e) {
			e.printStackTrace();
//			builder.append(getErrorJsonString(
//					ViewConstants.EXCEPTION_ERROR_CODE,
//					context.getString(R.string.exception_text)));
		}
		Log.d("requesting URL------", builder.toString());
		return builder.toString();
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			if (checkNetworkUtil.isNetAvailable()) {
				if (params.length > 0) {
					String jsonRequest = params[0];
					String url = params[1];
					return executePost(jsonRequest, url);
				}
			} else {
				// showToast("No network available");
				return getErrorJsonString(ViewConstants.ERROR_NO_NETWORK,
						context.getString(R.string.no_network_available));
			}
		} catch (Exception e) {
			Log.e("Exception", " WebClient doInBackground()");
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * This is the method for getErrorJsonString.
	 * </p>
	 * @param errorCode the error code
	 * @param errorText the error string
	 * @return errorJson the jsonString
	 */
	private String getErrorJsonString(String errorCode, String errorText) {
		try {
			JSONObject errorJson = new JSONObject();
			errorJson.put("success", 0);

			JSONArray errorArray = new JSONArray();
			JSONObject errorObject = new JSONObject();
			errorObject.put("errorText", errorText);
			errorObject.put("errorCode", errorCode);
			// errorObject.put("errorText",
			// context.getString(R.string.no_network_available));
			// errorObject.put("errorCode", ViewConstants.ERROR_NO_NETWORK);

			errorArray.put(errorObject);
			errorJson.put("errorMsg", errorArray);
			return errorJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String serverResponse) {
		serverResponseHandler.onServerResponse(serverResponse);
		super.onPostExecute(serverResponse);
	}
}
