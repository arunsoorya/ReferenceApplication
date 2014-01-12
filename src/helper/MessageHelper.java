package helper;
import com.example.webviewcache.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MessageHelper {

	private void MessageHelper() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * <p>
	 * This is the method for dismiss the progress dialog.
	 * </p>
	 * 
	 * @param message the message
	 */
	public void showToast(Context context, String message) {
		try {
			/** The layout inflator. */
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			View toastLayout = inflater.inflate(R.layout.custom_toast_layout, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout));
			Toast toast = new Toast(context);
			toast.setDuration(message.length());
			toast.setView(toastLayout);
			toast.setGravity(Gravity.CENTER, 0, 0);
			TextView tv = (TextView) toastLayout.findViewById(R.id.toast_text);
			tv.setText(message);
			toast.show();
			// Toast.makeText(context, message, message.length()).show();
		} catch (Exception e) {
			Log.d("Exception ViewHelper********** ", "----------showToast-------------- "
					+ e);
		}
	}
}
