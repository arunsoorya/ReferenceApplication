/*
 * @(#)BaseActivity.java}
 *
 * Copyright (c) 2011 Experion Technologies Pvt. Ltd.
 * 407,Thejaswini, Technopark Campus, Trivandrum 695 581
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Experion Technologies Pvt. Ltd. You shall not disclose such
 * Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into
 * with Experion Technologies.
 */
package com.barbourbooks.biblecrosswordpuzzles;

import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.barbourbooks.biblecrosswordpuzzles.helper.PuzzlePref;
import com.barbourbooks.biblecrosswordpuzzles.inappUtil.IabHelper;
import com.barbourbooks.biblecrosswordpuzzles.inappUtil.IabResult;
import com.barbourbooks.biblecrosswordpuzzles.inappUtil.Purchase;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public abstract class BaseActivity extends FragmentActivity {

	IabHelper mHelper;
	String SKU_GAS = "com.barbourbooks.biblecrosswordpuzzles.morepuzzles";

	/** This method is called when the activity is first created. */
	// private MainFragment mainFragment;
	private static final String TAG = "MainFragment";
	private ActivityFinishReceiver activityFinishReceiver;
	private UiLifecycleHelper uiHelper;
	private Context context;
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	String url = "http://www.biblegateway.com/";
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiUkan+ng/WQKVBE9znDZtKFx/kFVW12sv+/6krxShDeRwJI6zn8984zBdNhUJvfwFDzvMOqxRQ61AReWLPzVzmKCcDM32dB1GJ/vzyo6yOG5wk8idZ/2lsRmrufmA0IaFPHveq1WzvBGDIkL3KVwkOxQuQyJTN0iGFlRObGXgCvmTHzHqpLA5Tefy4uCBIcTorIrhX2g0bYWmiHHLhcArXmh3NTHYlbsLS3SugrTlFiKBNqECIVINrA6Bo2mOF/erDUwyj0yGex0COQ7j3wKbtyQ+ft3bX7ZySzCRt7JHqVS7bmhA3L+C1yb9sJ3mpiRfaT4/echgpVhs19aqbHDFQIDAQAB";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerActivityFinishReceiver();

		context = this;
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
		}
		// compute your public key and store it in base64EncodedPublicKey
		mHelper = new IabHelper(this, base64EncodedPublicKey);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					Log.d(TAG, "Problem setting up In-app Billing: " + result);
				}
				// Hooray, IAB is fully set up!
			}
		});

	}

	protected void purchase() {
		mHelper.launchPurchaseFlow(this, SKU_GAS, 10001,
				mPurchaseFinishedListener,
				"bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
	}

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if (result.isFailure()) {
				Log.d(TAG, "Error purchasing: " + result);
				return;
			} else if (purchase.getSku().equals(SKU_GAS)) {
				PuzzlePref.savePurchasePref(context, true);
				Toast.makeText(BaseActivity.this, "Item purchased",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	protected void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	protected void performPublish() {
		Session session = Session.getActiveSession();
		if (session != null) {
			if (hasPublishPermission()) {
				// We can do the action right away.
				postStatusUpdate();
			} else {
				// We need to get new permissions, then complete the action when
				// we get called back.
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						this, PERMISSIONS));
			}
		}
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null
				&& session.getPermissions().contains("publish_actions");
	}

	private void postStatusUpdate() {
		String title = "I’m playing the Bible Crossword Puzzles app!";
		String Content = "Want to compare puzzle scores? Let’s see who knows more about the Bible. Get the app today so we can see who the best player is.";
		// Link: [this should be the link to the app in iTunes for Apple app.
		// Google Play store for Android]

		Bundle params = new Bundle();
		params.putString("name", getString(R.string.app_name));
		params.putString("caption", title);
		params.putString("description", Content);
		params.putString("link", "www.google.com");

		// Invoke the dialog
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(this,
				Session.getActiveSession(), params)).setOnCompleteListener(
				new OnCompleteListener() {
					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(BaseActivity.this,
										"Story published: ", Toast.LENGTH_SHORT)
										.show();
							}
						}

					}

				}).build();
		feedDialog.show();
		// final String message = "check";
		// Request request = Request.newStatusUpdateRequest(
		// Session.getActiveSession(), message, new Request.Callback() {
		// @Override
		// public void onCompleted(Response response) {
		// showPublishResult(message, response.getGraphObject(),
		// response.getError());
		// }
		// });
		// request.executeAsync();
	}

	private void showPublishResult(String message, GraphObject result,
			FacebookRequestError error) {
		String title = null;
		String alertMessage = null;
		if (error == null) {
			title = "success";
			// String id = result.cast(GraphObjectWithId.class).getId();
			alertMessage = "Successfully posted";
		} else {
			title = "Error posting message";
			alertMessage = error.getErrorMessage();
		}

		new AlertDialog.Builder(this).setTitle(title).setMessage(alertMessage)
				.setPositiveButton("Ok", null).show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(activityFinishReceiver);
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
		super.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();
		// Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		// Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	/**
	 * This is the method that calls when user clicks home button. But will not
	 * call when activity automatically goes to background as a result of an
	 * incoming call.
	 */
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		/*
		 * The below two line can be also write in onPause() instead of here.
		 * But if we right there, the appComingFromBackground() will call after
		 * a call has come.
		 */

	}

	/**
	 * 
	 * <p>
	 * This is the method for navigating screen from left to right, starting
	 * state..
	 * </p>
	 */
	protected void slideFromRight() {
	}

	/**
	 * 
	 * <p>
	 * This is the method for navigating screen from right to left, finishing
	 * state.
	 * </p>
	 */
	protected void slideToLeft() {
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * 
	 * @return
	 */
	protected Context getCurrentContext() {
		return this;
	}

	/**
	 * 
	 * <p>
	 * This is the method for .
	 * </p>
	 * 
	 * @param editText
	 */
	protected void hideKeyBoard(EditText editText) {
		final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public void navigatetoHome() {

		broadCastMessage("goToHome");
		startActivity(new Intent(this, GameSelection.class));
	}

	public void broadCastMessage(String callBackIdentifier) {
		Intent intent = new Intent(callBackIdentifier);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setType("text/plain");
		sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * This is the method for registerActivityFinishReceiver.
	 * </p>
	 */
	private void registerActivityFinishReceiver() {
		activityFinishReceiver = new ActivityFinishReceiver();
		try {
			registerReceiver(activityFinishReceiver,
					IntentFilter.create("goToHome", "text/plain"));
		} catch (Exception e) {
			Log.d("Exception BaseActivity", "registerActivityFinishReceiver() "
					+ e);
		}
	}

	/**
	 * 
	 * <p>
	 * This is the method for unRegisterActivityFinishReceiver.
	 * </p>
	 */
	private void unRegisterActivityFinishReceiver() {
		try {
			unregisterReceiver(activityFinishReceiver);
		} catch (Exception e) {
			Log.d("Exception BaseActivity",
					"unRegisterActivityFinishReceiver() " + e);
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (session.isOpened()) {
				performPublish();
			}
			if (exception != null) {
				Toast.makeText(BaseActivity.this, "" + exception.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private final class ActivityFinishReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				finish();
			} catch (Exception e) {
				Log.d("Exception BaseActivity",
						"ActivityFinishReceiver onReceive" + e);
			}
		}
	}
}