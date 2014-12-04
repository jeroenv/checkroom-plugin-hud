package com.checkroom.plugin.hud;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HUDDialog extends Dialog {
	// UI
	private ImageView ivIcon;
	private ProgressBar pbLoading;
	private TextView tvText;

	private String mText;
	private String mDrawableResourceName;
	private int mTimeOut;

	private Activity mContext;

	public HUDDialog(Activity a, String text, String imageResourceName,
			int timeOut) {
		super(a);
		mText = text;
		mDrawableResourceName = imageResourceName;
		mTimeOut = timeOut;
		mContext = a;
		this.setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(getIdFromProjectsRFile(RESOURCE_TYPE_LAYOUT,
				"dialog_hud"));
		setWidgets();
	}

	private void setWidgets() {
		ivIcon = (ImageView) findViewById(getIdFromProjectsRFile(
				RESOURCE_TYPE_ID, "ivHudPlugin"));
		pbLoading = (ProgressBar) findViewById(getIdFromProjectsRFile(
				RESOURCE_TYPE_ID, "pbHudPlugin"));
		tvText = (TextView) findViewById(getIdFromProjectsRFile(
				RESOURCE_TYPE_ID, "tvHudPlugin"));

		setupImageViewAndProgressBar();

		setupTextView();

		closeDialogForTimeOut();
	}

	private void setupImageViewAndProgressBar() {
		if (mDrawableResourceName.equals("")) {
			ivIcon.setVisibility(View.INVISIBLE);
			pbLoading.setVisibility(View.VISIBLE);
		} else {
			pbLoading.setVisibility(View.INVISIBLE);
		}
	}

	private void setupTextView() {
		if (mText.equals("")) {
			tvText.setVisibility(View.GONE);
		} else {
			tvText.setText(mText);
			tvText.setVisibility(View.VISIBLE);
		}
	}

	private void closeDialogForTimeOut() {
		if (mTimeOut != 0) {
			new CountDownTimer(mTimeOut, 1000) {

				public void onTick(long millisUntilFinished) {
				}

				public void onFinish() {
					HUDDialog.this.cancel();
				}
			}.start();
		}
	}

	/* Callback interface */
	public interface HUDDialogCallback {
		public void onCloseHud();
	}

	/*
	 * R.java util
	 */
	private static final String RESOURCE_TYPE_LAYOUT = "layout";
	private static final String RESOURCE_TYPE_ID = "id";
	private static final String RESOURCE_TYPE_DRAWABLE = "drawable";
	private String packageName;

	private int getIdFromProjectsRFile(String resourceType, String resourceId) {
		if (packageName == null) {
			try {
				PackageManager pm = mContext.getPackageManager();
				PackageInfo packageInfo = pm.getPackageInfo(
						mContext.getPackageName(), 0);
				packageName = packageInfo.packageName;
			} catch (NameNotFoundException e) {
			}
		}
		Resources resources = mContext.getApplicationContext().getResources();
		return resources.getIdentifier(resourceId, resourceType, packageName);
	}
}