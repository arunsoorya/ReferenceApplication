package helper;
/*@(#)ImageAttachmentHelper.java
 */


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


public class ImageCapture {
	/** The Image name. */
	private static String TEMP_PHOTO_FILE = "NAME OF FOLDER";
	/** The context of the calling activity. */
	private Context context;
	/** sdCardHelper. */
	private SdCardHelper sdCardHelper;
	/** viewHelper. */
	private MessageHelper messageHelper;

	/** The constant PICK_FROM_FILE. */
	private static final int PICK_FROM_FILE = 1;
	private static final int PROFILE_CAMERA_PIC = 2;
	private static final int PROFILE_GALLERY_PIC = 3;
	private static final int PROFILE_CAMERA_SAVE = 4;
	/** The image uri. */
	private Uri imageCaptureUri;
	/** The image uri. */
	// public static Uri cameraImageCaptureUri;
	/** The current Imag. */
	public String errorMessage = "Whoops - your device doesn't have SDCARD!";

	/**
	 * Constructor for UploadPhotoHelper.
	 * @param context
	 *        for Context
	 */
	public ImageCapture(Context context) {
		this.context = context;
		sdCardHelper = new SdCardHelper(context);
	}

	

	/*
	 * The method for get image path.
	 * @param uri of image
	 * @return image path string
	 */
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	/**
	 * This is the method for deleting image from sdcard.
	 * @param imageName
	 *        imageName
	 */
	public void deleteImageFromMemory(String imageName) {
		// File file = getImagePath(imageNo);
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"
						+ TEMP_PHOTO_FILE, imageName);
		file.delete();
	}

	/**
	 * <p>
	 * This is the method for deleting all images.
	 * </p>
	 */
	public void deleteAllImages() {
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"
						+ TEMP_PHOTO_FILE);

		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				DeleteRecursive(child);
			}
		}

		file.delete();
	}

	/**
	 * <p>
	 * This is the method for .
	 * </p>
	 * @param fileOrDirectory
	 */
	void DeleteRecursive(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				DeleteRecursive(child);

		fileOrDirectory.delete();
	}

	/**
	 * This is the method for take from camera.
	 * @param selImage
	 *        selImage
	 */
	public void onTakeFromCamera(Activity frag, int selImage) {
		if (sdCardHelper.isSDCARDMounted()) {
			createDirIfNotExists();
			openCamera(frag, selImage);
		} else {
			showToast(errorMessage, frag);
		}
	}

	/**
	 * This is the method for setting the image orientation when image load
	 * through camera application.
	 * @param imagePath
	 *        imagePath
	 * @param bitmap
	 *        bitmap
	 * @return
	 */
	public Bitmap fixtheOrientationOfImage1(String imagePath, Bitmap bitmap) {
		ExifInterface exif = getTheImageOrientation(imagePath);
		if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")) {
			bitmap = rotate(bitmap, 90);
		} else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")) {
			bitmap = rotate(bitmap, 270);
		} else if (exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")) {
			bitmap = rotate(bitmap, 180);
		}
		return bitmap;
	}

	/**
	 * This is the method for rotating the image with the given digree.
	 * @param bitmap
	 * @param degree
	 * @return
	 */
	private Bitmap rotate(Bitmap bitmap, int degree) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix mtx = new Matrix();
		mtx.postRotate(degree);

		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

	public ExifInterface getTheImageOrientation(String imagePath) {
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			return exif;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * <p>
	 * This is the method to calculate a the sample size value based on a target
	 * width and height.
	 * </p>
	 * @param options
	 *        options
	 * @param reqWidth
	 *        width
	 * @param reqHeight
	 *        height
	 * @return
	 */
	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * First decode with inJustDecodeBounds set to true, pass the options
	 * through and then decode again using the new inSampleSize value and
	 * inJustDecodeBounds set to false: .
	 * @param imagePath
	 *        imagePath
	 * @param reqWidth
	 *        reqWidth
	 * @param reqHeight
	 *        reqHeight
	 * @return
	 */
	public Bitmap decodeSampledBitmapFromResource(String imagePath, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		BitmapFactory.decodeFile(imagePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imagePath, options);
	}

	/**
	 * This is the method for calculating dimensions.
	 * @param viewid
	 *        viewid
	 * @return
	 */
	public Point calculateImageDimensions(Context context, final int viewid) {
		Point dimen = new Point();

		Bitmap bitmapOrg = BitmapFactory.decodeResource(context.getResources(), viewid);

		dimen.x = bitmapOrg.getWidth();
		dimen.y = bitmapOrg.getHeight();

		Log.d("width", "" + dimen.x);
		Log.d("height", "" + dimen.y);
		return dimen;
	}


	/**
	 * The method to open camera.
	 * @param imageCaptureUri
	 *        - image url
	 */
	private final void openCamera(Activity frag, int selectedImageNo) {
		try {

			String imageName = createImageName();
			imageCaptureUri = Uri.fromFile(getImageFile(imageName));

			// use standard intent to capture an image
			Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// we will handle the returned data in onActivityResult

			captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCaptureUri);
			captureIntent.putExtra("return-data", true);
			frag.startActivityForResult(captureIntent, PROFILE_CAMERA_PIC);
		} catch (ActivityNotFoundException e) {
			showToast(errorMessage, frag);
		}
	}

	/**
	 * <p>
	 * This is the method to open gallery ..
	 * </p>
	 * @param frag
	 *        frag
	 * @param selImage
	 *        selImage
	 */
	public final void openImageGallery(Activity frag, int selImage) {

		if (sdCardHelper.isSDCARDMounted()) {

			createDirIfNotExists();
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			photoPickerIntent.setType("image/*");

			photoPickerIntent.putExtra("crop", "true");
			String imageName = createImageName();

			photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(getImageFile(imageName)));
			photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			frag.startActivityForResult(photoPickerIntent, PROFILE_GALLERY_PIC);
		} else {
			showToast(errorMessage, frag);
		}
	}

	/**
	 * <p>
	 * This is the method for checking the sdcard.
	 * </p>
	 * @return
	 */
	public final boolean isSDCardMounted() {
		return sdCardHelper.isSDCARDMounted();
	}


	/**
	 * This is the method for calling the standard crop action intent.
	 */
	public void cameraCrop(Activity frag, int selectedImageNo) {
		Intent cropIntent = new Intent("com.android.camera.action.CROP");

		imageCaptureUri = Uri.fromFile(getImageFile(createImageName()));

		cropIntent.setDataAndType(imageCaptureUri, "image/*");
		List<ResolveInfo> list = frag.getPackageManager()
				.queryIntentActivities(cropIntent, 0);
		// check if the native crop present.

		final Activity homeScreen = (Activity) frag;
		if (isCropFeatureAvailable(list)) {

			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("output", imageCaptureUri);
			cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			frag.startActivityForResult(cropIntent, PROFILE_CAMERA_SAVE);
			if (list.size() == 1) {
				// try {
				// Intent i = new Intent(cropIntent);
				// ResolveInfo res = list.get(0);
				// i.setComponent(new
				// ComponentName(res.activityInfo.packageName,
				// res.activityInfo.name));
				//
				// frag.startActivityForResult(cropIntent,
				// ViewConstants.PROFILE_CAMERA_SAVE);
				// } catch (ActivityNotFoundException e) {
				// frag.onActivityResult(ViewConstants.PROFILE_CAMERA_SAVE,
				// Activity.RESULT_OK,
				// null);
				// e.printStackTrace();
				// }
			} else {

				try {
					findOtherCropApplication(frag, list, cropIntent);
				} catch (Exception e) {
//					frag.onActivityResult(PROFILE_CAMERA_SAVE, Activity.RESULT_OK,
//							null);
					e.printStackTrace();
				}
			}

		} else {
			//
			showToast("Croping",
					frag);
//			frag.onActivityResult(PROFILE_CAMERA_SAVE, Activity.RESULT_OK, null);
		}

	}

	/**
	 * <p>
	 * This is the method for showToast.
	 * </p>
	 * @param message
	 *        the message
	 * @param activity
	 *        the activity
	 */
	private void showToast(String message, Context activity) {
		// viewHelper.showToast(message);
		messageHelper.showToast(activity, message);
	}

	/**
	 * <p>
	 * This is the method for checking whether the crop app exist or not.
	 * </p>
	 * @param list
	 * @return
	 */
	private boolean isCropFeatureAvailable(List<ResolveInfo> list) {

		int size = list.size();

		if (size == 0) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * This is the method for .
	 * </p>
	 * @param frag
	 * @param list
	 * @param cropIntent
	 */
	private void findOtherCropApplication(final Activity frag, List<ResolveInfo> list, Intent cropIntent) {
		final Context context = frag;
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
		for (ResolveInfo res : list) {
			final CropOption co = new CropOption();

			co.title = context.getPackageManager().getApplicationLabel(
					res.activityInfo.applicationInfo);
			co.icon = context.getPackageManager().getApplicationIcon(
					res.activityInfo.applicationInfo);
			co.appIntent = new Intent(cropIntent);

			co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName,
					res.activityInfo.name));

			cropOptions.add(co);
		}

		CropOptionAdapter adapter = new CropOptionAdapter(context, cropOptions);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Crop");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				frag.startActivityForResult(cropOptions.get(item).appIntent,
						PROFILE_CAMERA_SAVE);
			}
		});

		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// if (imageCaptureUri != null) {
				// context.getContentResolver().delete(imageCaptureUri, null,
				// null);
				// imageCaptureUri = null;
				// }
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * This is the method for getting the image path.
	 * @param imageNo
	 *        imageNo
	 * @return
	 */
	public final File getImagePath(int imageNo) {
		String imageName = createImageName();
		if (imageName == null || imageName.trim().length() == 0) {
			return null;
		}
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				TEMP_PHOTO_FILE + "/" + imageName);
		return file;

	}

	/**
	 * <p>
	 * This is the method for setting image name.
	 * </p>
	 * @param imageNo
	 *        imageNo
	 * @return
	 */
	public String createImageName() {
		return TEMP_PHOTO_FILE + System.currentTimeMillis() + ".jpg";
	}

	/**
	 * <p>
	 * This is the method for create a new directory.
	 * </p>
	 * @return boolean true or false
	 */
	public final boolean createDirIfNotExists() {
		boolean isSuccess = true;
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"
						+ TEMP_PHOTO_FILE);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.e("SdCardHelper Log :: ", "Problem creating Image folder");
				isSuccess = false;
			}
		}
		return isSuccess;
	}

	/**
	 * This is the method for .
	 * @param name
	 * @return
	 */
	public boolean isImageExistInSDcard(String name) {

		if (name == null || name.trim().length() == 0) {
			return false;
		}
		File file = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"
						+ TEMP_PHOTO_FILE + "/" + name);
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * This is the method for get temp file.
	 * </p>
	 * @param imageName
	 *        imageName
	 * @return f the file
	 */
	public File getImageFile(String imageName) {
		if (sdCardHelper.isSDCARDMounted()) {

			File file = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
							+ "/" + TEMP_PHOTO_FILE);
			if (!file.exists()) {
				file.mkdir();
			}
			File f = null;

			try {
				f = new File(file, imageName);
				f.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return f;
		} else {
			return null;
		}
	}
}
