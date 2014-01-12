/*
 * @(#)BaseListActivity.java}
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
package view.helper;

import java.util.HashMap;

import roboguice.activity.RoboListActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.inject.Key;

import constants.ViewConstants;

/**
 *
 * @author vipin.cb , vipin.cb@experionglobal.com
 * <br>Apr 2, 2013, 11:01:09 AM
 * <br>Package:- <b>com.topgolf.mobile.views</b>
 * <br>Project:- <b>TopGolfMobile</b>
 * <p>
 */
public abstract class BaseListActivity extends RoboListActivity {

	/** The activity finish receiver. */
	private ActivityFinishReceiver activityFinishReceiver;
	/** The scopedObjects. */
	protected HashMap<Key<?>, Object> scopedObjects = new HashMap<Key<?>, Object>();

	/** This method is called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerActivityFinishReceiver();
	}

	protected void onStart(Activity activity) {
		EasyTracker.getInstance().activityStart(activity);
	}

	protected void onStop(Activity activity) {
		EasyTracker.getInstance().activityStop(activity);
	}

	/**
	 * This is the method that calls when the user navigates back to the
	 * activity.
	 */
	@Override
	public void onStart() {
		super.onStart();
		ViewConstants.isToBackground = false;
    	if (ViewConstants.previousContext != null && ViewConstants.previousContext == this) {
			appComingFromBackground();
    	}
	}

	/**
	 * This is the method that calls when this activity comes to foreground from
	 * the event caused by a phone call or the phone get locked
	 */
	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * This is the method that calls when another activity comes foreground over
	 * this activity or a phone call occures or even when the phone get locked
	 */
	@Override
	public void onPause() {
		super.onPause();
		ViewConstants.previousContext = this;
		ViewConstants.isToBackground = true;
	}

	/**
	 * This is the method that calls when this activity is no longer visible due
	 * to user navigates to another activity.
	 */
	@Override
	public void onStop() {
		super.onStop();
    	if (ViewConstants.isToBackground) {
    		appGoingToBackground();
    	}
	}

	/**
	 * This is the method that calls to destroy the activity when user
	 * explicitly calls the finish() method or presses back button.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		unRegisterActivityFinishReceiver();
	}

	 /** This method is called when the application going to background. */
	 public abstract void appGoingToBackground();
	
	 /** This method is called when the application coming from background. */
	 public abstract void appComingFromBackground();

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
					IntentFilter.create(ViewConstants.FINISH, ViewConstants.INTENT_TYPE_BROADCAST));
		} catch (Exception e) {
			Log.d("Exception BaseListActivity",
					"registerActivityFinishReceiver() " + e);
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
			Log.d("Exception BaseListActivity",
					"unRegisterActivityFinishReceiver() " + e);
		}
	}

	/**
	 *
	 * @author vipin.cb , vipin.cb@experionglobal.com
	 * <br>Apr 2, 2013, 11:02:44 AM
	 * <br>Package:- <b>com.topgolf.mobile.views</b>
	 * <br>Project:- <b>TopGolfMobile</b>
	 * <p>
	 */
	private final class ActivityFinishReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				finish();
			} catch (Exception e) {
				Log.d("Exception BaseListActivity",
						"ActivityFinishReceiver onReceive" + e);
			}
		}
	}
}
