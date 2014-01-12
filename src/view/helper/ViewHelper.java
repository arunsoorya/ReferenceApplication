package view.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import navigationcontroller.NavigationConstants;
import navigationcontroller.NavigationController;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webviewcache.R;
import com.google.inject.Inject;

import constants.AppConstants;
import constants.ViewConstants;
import constants.ViewConstants.DateFormats;

public class ViewHelper {
	/** The context. */
	private static Context context;
	/** The view helper instance. */
	private static ViewHelper viewHelperInstance;
	/** The light typeface. */
	private static Typeface lightTypeface;

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = AppConstants.DATABASE_NAME;
	@Inject
	private NavigationController navigationController;

	/**
	 * 
	 * Constructor for ViewHelper.
	 * 
	 * @param context the context
	 */
	@Inject
	public ViewHelper(Context context) {
		this.context = context;
		extractFonts();
		viewHelperInstance = this;
	}

	public static ViewHelper getInstance(Context context) {
		if (viewHelperInstance == null) {
			viewHelperInstance = new ViewHelper(context);
		}
		return viewHelperInstance;
	}


	/**
	 * 
	 * <p>
	 * This is the method for extractFonts.
	 * </p>
	 */
	private void extractFonts() {
		lightTypeface = Typeface.createFromAsset(context.getAssets(), "DINNextW01-Light.ttf");
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * 
	 * @return
	 */
	public boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase("/data/data/"
					+ context.getPackageName() + "/databases/"
					+ AppConstants.DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
			checkDB.close();
		} catch (SQLiteException e) {
			Log.d("SQLiteException ", "database doesn't exist yet. " + e);
		}
		return checkDB != null ? true : false;
	}

	/**
	 * 
	 * <p>
	 * This is the method for copyDataBase.
	 * </p>
	 * 
	 * @throws IOException
	 */
	public void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream inputStream = context.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = getDBPath(DATABASE_NAME) + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream outPutStream = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outPutStream.write(buffer, 0, length);
		}

		// Close the streams
		outPutStream.flush();
		outPutStream.close();
		inputStream.close();
	}

	/**
	 * 
	 * <p>
	 * This is the method for getDBPath.
	 * </p>
	 * 
	 * @param dbName the db name
	 * @return the db path
	 */
	private String getDBPath(String dbName) {
		// return context.getDatabasePath(dbName).getAbsolutePath();
		return "/data/data/" + context.getApplicationContext().getPackageName()
				+ "/databases/";
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * 
	 * @param view
	 */
	public Typeface getLightFont() {
		if (lightTypeface == null) {
			lightTypeface = Typeface.createFromAsset(context.getAssets(), "DINNextW01-Light.ttf");
		}
		return lightTypeface;
		// return Typeface.createFromAsset(context.getAssets(),
		// "DINNextW01-Light.ttf");
	}


	public ArrayAdapter<String> getSpinnerAdapter(List<String> intervalList, final boolean showHintColor, final Typeface typeface) {

		ArrayAdapter<String> intervalAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_view, intervalList) {
			Typeface fontStyle = typeface;

			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				if (fontStyle == null) {
					fontStyle = getLightFont();
				}
				((TextView) v).setTextAppearance(context, R.style.drop_down_select_text_style);
				((TextView) v).setTypeface(fontStyle);
				return v;

			}

			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				if (showHintColor) {
					((TextView) v).setTextAppearance(context, R.style.drop_down_select_text_style);
				} else {
					((TextView) v).setTextAppearance(context, R.style.drop_down_select_text_style);
				}
				if (fontStyle == null) {
					fontStyle = getLightFont();
				}
				((TextView) v).setTypeface(fontStyle);

				return v;
			}
		};
		intervalAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		return intervalAdapter;
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * 
	 * @param intervalList
	 * @param showHint
	 * @return
	 */
	public ArrayAdapter<String> getSpinnerAdapter(List<String> intervalList, final boolean showHintColor) {
		return getSpinnerAdapter(intervalList, showHintColor, null);
	}

	/**
	 * 
	 * <p>
	 * This is the method for broadcast the message.
	 * </p>
	 */
	public void broadCastMessage(String message, Map<String, Serializable> extras) {
		Intent intent = new Intent(message);
		intent.setType(ViewConstants.INTENT_TYPE_BROADCAST);
		if (extras != null) {
			for (Map.Entry<String, Serializable> entry : extras.entrySet()) {
				intent.putExtra(entry.getKey(), entry.getValue());
			}
		}
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * This is the method for sent broadcast intent.
	 * </p>
	 * @param callBackIdentifier
	 */
	public void broadCastMessage(String callBackIdentifier) {
		Intent intent = new Intent(callBackIdentifier);
		intent.setType("text/plain");
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * This is the method for clear the stack of activities.
	 * </p>
	 */
	public void clearStack() {
		broadCastMessage(ViewConstants.FINISH, null);
	}

	/**
	 * 
	 * @param view
	 */
	public void setViewVisibilityVisible(View view) {
		try {
			view.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Exception", " ViewHelper setViewVisibilityVisible" + e);
		}
	}

	/**
	 * 
	 * @param view
	 */
	public void setViewVisibilityInvisible(View view) {
		try {
			view.setVisibility(View.INVISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Exception", " ViewHelper setViewVisibilityInvisible" + e);
		}
	}

	/**
	 * 
	 * @param view
	 */
	public void setViewVisibilityGone(View view) {
		try {
			view.setVisibility(View.GONE);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Exception", " ViewHelper setViewVisibilityGone" + e);
		}
	}

	/**
	 * 
	 * <p>
	 * This is the method for getDipFromPixel.
	 * </p>
	 * 
	 * @param pixels the pixel value
	 * @return the dip
	 */
	public int getDipFromPixel(int pixels) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (pixels / density);
	}

	/**
	 * 
	 * <p>
	 * This is the method for getPixelFromDip.
	 * </p>
	 * 
	 * @param dip the dip value
	 * @return the pixel
	 */
	public int getPixelFromDip(int dip) {
		float density = context.getResources().getDisplayMetrics().densityDpi;
		int i = (int) (dip * (density / 160f));
		// return (int) (dip * (density/160f));

		// returns the number of pixels for 123.4dip
		int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) dip, context.getResources().getDisplayMetrics());
		// int value = (int)
		// TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		// (float) 123.4, getResources().getDisplayMetrics());
		return value;
	}

	/**
	 * 
	 * <p>
	 * This is the method for get the number in local formatted type.
	 * </p>
	 * 
	 * @param number the number to be formatted
	 * @return formattedValue the formatted number
	 */
	public String getLocalFormattedNumber(String number) {
		try {
			int amount = Integer.parseInt(number);
			// NumberFormat formatter = new DecimalFormat("###,###,###.##");
			// String formattedValue = formatter.format(amount);
			String formattedValue = NumberFormat.getIntegerInstance().format(amount);
			return formattedValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}

	/**
	 * 
	 * <p>
	 * This is the method for get the number in local formatted type.
	 * </p>
	 * 
	 * @param number the number to be formatted
	 * @return formattedValue the formatted number
	 */
	public void navigateToGoogleMap(Context context, String sourceLat, String sourceLong, String destLat, String destLong) {
		try {
			String uri = "http://maps.google.com/maps?saddr=" + sourceLat + ","
					+ sourceLong + "&daddr=" + destLat + "," + destLong;
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
			intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * <p>
	 * This is the method for getting date in millis to specific format.
	 * </p>
	 * 
	 * @param milliSeconds
	 * @param dateFormat
	 * @return
	 */
	public String getDate(long milliSeconds, String dateFormat) {
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 
	 * <p>
	 * This is the method for getting date in millis to specific format.
	 * </p>
	 * 
	 * @param milliSeconds
	 * @param dateFormat
	 * @return
	 */
	public String getGraphViewDate(String dateString) {
		// public String getGraphViewDate(String dateString, String dateFormat)
		// {
		try {
			DateFormat formatter = new SimpleDateFormat(DateFormats.YMD_HYPHEN.toString());
			DateFormat formatterMD = new SimpleDateFormat(DateFormats.MMMD_SPACE.toString());
			DateFormat formatterY = new SimpleDateFormat(DateFormats.YYYY.toString());
			Date date = formatter.parse(dateString);
			return formatterMD.format(date) + "\n" + formatterY.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String formatDateForQuickStat(String input) {

		Date format = formatDate(input, DateFormats.DDMMYYYY_HYPHEN.toString());
		// Date format = viewHelper.formatDate(input, "dd-MM-yyyy");
		java.text.DateFormat outputformat = new SimpleDateFormat("dd-MMM-yyyy");

		return outputformat.format(format);
	}

	/**
	 * 
	 * <p>
	 * This is the method for getting date in millis to specific format.
	 * </p>
	 * 
	 * @param milliSeconds
	 * @param dateFormat
	 * @return
	 */
	public Date formatDate(String input, String dateFormat) {
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		Date date = null;
		try {
			date = formatter.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * <p>
	 * This is the method for get the MD5 string for the given input string.
	 * </p>
	 * 
	 * @param password the password
	 * @return the MD5 string
	 */
	public String getMD5OfString(String password) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance(ViewConstants.MD5_ALGORITHM);
			digest.update(password.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);
				while (h.length() < 2)
					h = ViewConstants.CONSTANT_ZERO + h;
				hexString.append(h);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}


	public String formatBalancePrecision(String value) {
		return String.format("%.2f", getDoubleOfString(value));
	}

	public String formatBalancePrecision(Double value) {
		return String.format("%.2f", value);
	}

	public Double getDoubleOfString(String value) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getFormattedAvgPoints(double avgPoints) {
		try {
			return String.format("%.2f", avgPoints);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
