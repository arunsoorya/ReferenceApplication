package helper;
/*@(#)DialogHelper.java}
 */


import com.example.webviewcache.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
public class DialogHelper {
	/** The context. */
	private Context context;

	/**
	 * Constructor for DialogHelper.
	 */
	public DialogHelper(Context context) {
		this.context = context;
	}

	/**
	 * <p>
	 * This is the method for showing alert.
	 * </p>
	 * @param title title
	 * @param message message
	 * @param buttonText buttonText
	 */
	public AlertDialog getAlertDialog(String title, String message,
			String buttonText) {
		AlertDialog.Builder builer = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.alertTheme));
		builer.setTitle(title);
		builer.setMessage(message);
		builer.setNegativeButton(buttonText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		return builer.create();
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * @param tittle
	 * @param message
	 */
	public void showPositiveDialog(String tittle, String message) {
		AlertDialog.Builder builer = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.alertTheme));
		if (tittle != null) {
			builer.setTitle(tittle);
		}
		builer.setMessage(message);
		builer.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builer.create().show();
	}

	public void showErrorDialog(String message){
		showPositiveDialog(context.getResources().getString(R.string.error), message);
	}
	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * @param tittle
	 * @param message
	 */
	public void showDialog(String tittle, String message) {
		AlertDialog.Builder builer = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.alertTheme));
		if (tittle != null) {
			builer.setTitle(tittle);
		}
		builer.setMessage(message);
		builer.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builer.setNegativeButton(context.getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builer.create().show();
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 */
	public AlertDialog.Builder showLogoutDialog() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.alertTheme));
//		alertDialog.setTitle(R.string.logout);
//		alertDialog.setMessage(R.string.logout_text);
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});

		// Showing Alert Message
		return alertDialog;
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 */
	public AlertDialog.Builder applicationExitPrompt() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				new ContextThemeWrapper(context,
						R.style.alertTheme));
		alertDialog.setTitle("Alert");
		alertDialog.setMessage("Do you want to exit the application?");
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to invoke NO event
						dialog.cancel();
					}
				});

		// Showing Alert Message
		return alertDialog;
	}

	/**
	 * <p>
	 * This is the method for showing alert with multiple options.
	 * </p>
	 * @param title title
	 * @param message message
	 * @param positiveText positive button text
	 * @param negativeText negative button text
	 */
	public AlertDialog getMultipleOptionAlertDialog(String title,
			String message, String positiveText, String negativeText) {
		AlertDialog.Builder builer = new AlertDialog.Builder(new ContextThemeWrapper(context,
				R.style.alertTheme));
		builer.setTitle(title);
		builer.setMessage(message);
		builer.setNegativeButton(negativeText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		builer.setPositiveButton(positiveText,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		return builer.create();
	}
}
