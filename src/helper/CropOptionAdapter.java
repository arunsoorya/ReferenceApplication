package helper;
/*@(#)CropOptionAdapter.java}
 */

import java.util.ArrayList;

import com.example.webviewcache.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CropOptionAdapter extends ArrayAdapter<CropOption> {
	/** title arraylist. */
	private ArrayList<CropOption> mOptions;
	/** mInflater layout inflater. */
	private LayoutInflater mInflater;

	/**
    * <p>This is the method for crop Option.</p>
    * @param context current context.
    * @param options for arraylist
    */
	public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
		super(context, R.layout.crop_selector, options);

		mOptions 	= options;

		mInflater	= LayoutInflater.from(context);
	}

	/**
    * <p>This is the method for getView.</p>
    * @param position for current data.
    * @param convertView for view
    * @param group for ViewGroup
    * @return convertView for view
    */
	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.crop_selector, null);
		}

		CropOption item = mOptions.get(position);

		if (item != null) {
			((ImageView) convertView.findViewById(R.id.iv_icon)).setImageDrawable(item.icon);
			((TextView) convertView.findViewById(R.id.tv_name)).setText(item.title);

			return convertView;
		}

		return null;
	}
}