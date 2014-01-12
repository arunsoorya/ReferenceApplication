package helper;
/*@(#)SdCardHelper.java
 */


import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
public class SdCardHelper {
    /** The context. */
	private Context context;
	/**
	 *
	 * Constructor for SdCardHelper.
	 * @param context the context
	 */
//	@Inject
//	public SdCardHelper(Context context) {
//		this.context = context;
//	}
	public SdCardHelper(Context context) {
		this.context = context;
	}

	/**
	 *
	 * <p>This is the method for get the sd card state.</p>
	 * @return media mounted status
	 */
	public final boolean isSDCARDMounted() {
       String status = Environment.getExternalStorageState();
       if (status.equals(Environment.MEDIA_MOUNTED)) {
           return true;
       }
       return false;
    }

	/**
	 *
	 * <p>This is the method for create a new directory.</p>
	 * @param path the path
	 * @return boolean true or false
	 */
	public final boolean createDirIfNotExists(String path) {
	    boolean isSuccess = true;
	    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), path);
//	    File file = new File(Environment.getExternalStorageDirectory(), path);
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            Log.e("SdCardHelper Log :: ", "Problem creating Image folder");
	            isSuccess = false;
	        }
	    }
	    return isSuccess;
	}

	/**
	 *
	 * <p>This is the method for create a new directory in application storage.</p>
	 * @param path the path
	 * @return boolean true or false
	 */
	public boolean createDirIfNotExistsInAppStorage(String path) {
		boolean isSuccess = true;
		File file = context.getDir(path, Context.MODE_WORLD_WRITEABLE);
		file.mkdirs();
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            Log.e("SdCardHelper Log :: ", "Problem creating Image folder");
	            isSuccess = false;
	        }
	    }
		return isSuccess;
	}


}
