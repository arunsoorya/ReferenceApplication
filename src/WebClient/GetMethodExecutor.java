package WebClient;
/*@(#)GetMethodExecutor.java}
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import network.CheckNetworkUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import WebClient.helper.ServerResponseHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.webviewcache.R;

import constants.ViewConstants;

public class GetMethodExecutor extends AsyncTask<String, String, String> {
	/** The context. */
	private Context context;
	/** The interface ServerResponseHandler. */
	private ServerResponseHandler serverResponseHandler;
	/** The checkNetworkUtil. */
	private CheckNetworkUtil checkNetworkUtil;
	/** The jsonString. */
	private String jsonString;
	/** The apiString. */
	private String apiString;

	/**
	 * 
	 * Constructor for PostMethodExecutor.
	 * 
	 * @param context
	 *            the context
	 * @param serverResponseHandler
	 *            the serverResponseHandler
	 */
	public GetMethodExecutor(Context context) {
		this.context = context;

		checkNetworkUtil = new CheckNetworkUtil(context);
	}

	/**
	 * 
	 * <p>
	 * This is the method for initializing the listener.
	 * </p>
	 * @param serverResponseHandler
	 */
	public void setServerResponseHandler(
			ServerResponseHandler serverResponseHandler) {
		this.serverResponseHandler = serverResponseHandler;
	}

	/**
	 * 
	 * <p>
	 * This is the method for initializing the request parameter for api call.
	 * </p>
	 * @param jsonString
	 * @param apiString
	 */
	public void setRequestParameter(String jsonString, String apiString) {
		this.jsonString = jsonString;
		this.apiString = apiString;
	}

	/**
	 * 
	 * <p>
	 * This is the method for execute the api.
	 * </p>
	 * 
	 * @param jsonString
	 *            the request json string with parameter
	 * @param apiString
	 *            the api string with url
	 * @return flag - true or false to indicate whether the fuction call was
	 *         success or failure
	 */
	public void executeApi() {
		try {
//			CheckNetworkUtil checkNetworkUtil = new CheckNetworkUtil(context);
//			if (checkNetworkUtil.isNetAvailable()) {
				String[] strArray = new String[2];
				strArray[0] = jsonString;
				strArray[1] = apiString;
				this.execute(strArray);
//				return MessageEnum.SUCCESS;
//			} else {
//				return MessageEnum.NO_NETWORK;
//			}
		} catch (Exception e) {
			Log.e("Exception", " GetMethodExecutor executeApi() " + e);
//			return MessageEnum.ERROR;
		}
	}

	private ArrayList<NameValuePair> getNameValuePairFromJsonString(
			String jsonRequestString) {
		try {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			JSONObject requestJsonObject = new JSONObject(jsonRequestString);
			Iterator<String> keys = requestJsonObject.keys();
			// JSONArray images = new JSONArray();
			while (keys.hasNext()) {
				String key = keys.next();
				String value = requestJsonObject.optString(key);
				nameValuePairs.add(new BasicNameValuePair(key, value));
			}
			return nameValuePairs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * This is the method for executeGet.
	 * </p>
	 * 
	 * @param jsonRequestString
	 *            the json request string
	 * @param urlString
	 *            the url string
	 * @return builder the string
	 */
	public String executeGet(String jsonRequestString, String urlString) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		
		ArrayList<NameValuePair> nameValuePairs = getNameValuePairFromJsonString(jsonRequestString);
		String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		HttpGet httpGet = new HttpGet(urlString + paramString);

		Log.d("requesting URL------", urlString + paramString);
		Log.d("sending data------", jsonRequestString.toString());
		try {

			HttpResponse response = client.execute(httpGet);
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
				builder.append(getErrorJsonString(
						ViewConstants.INTERNAL_SERVER_ERROR,
						context.getString(R.string.internal_server_error)));
				Log.e("Get was not successfull", "Status code is " + statusCode);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			builder.append(getErrorJsonString(
					ViewConstants.EXCEPTION_ERROR_CODE,
					context.getString(R.string.exception_text)));
		} catch (IOException e) {
			e.printStackTrace();
			builder.append(getErrorJsonString(
					ViewConstants.EXCEPTION_ERROR_CODE,
					context.getString(R.string.exception_text)));
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
					return executeGet(jsonRequest, url);
				}
			} else {
				// showToast("No network available");
				return getErrorJsonString(ViewConstants.ERROR_NO_NETWORK,
						context.getString(R.string.no_network_available));
			}
		} catch (Exception e) {
			Log.e("Exception", " GetMethodExecutor doInBackground()");
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * This is the method for getErrorJsonString.
	 * </p>
	 * 
	 * @param errorCode
	 *            the error code
	 * @param errorText
	 *            the error string
	 * @return errorJson the jsonString
	 */
	private String getErrorJsonString(String errorCode, String errorText) {
		try {
			JSONObject errorJson = new JSONObject();

			JSONObject errorObject = new JSONObject();
			errorObject.put("code", errorCode);
			errorObject.put("msg", errorText);

//			errorArray.put(errorObject);
			errorJson.put("response", errorObject);

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
