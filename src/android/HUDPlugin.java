package com.checkroom.plugin.hud;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.WindowManager;

public class HUDPlugin extends CordovaPlugin {
	private final String pluginName = "HUD";

	private static final String ARG_TEXT = "text";
	private static final String ARG_ICON = "icon";
	private static final String ARG_TIME_OUT = "timeOut";

	private String mText;
	private String mIcon;
	private int mTimeOut;

	private CallbackContext callbackContext;

	private HUDDialog mHUDDialog;

	@Override
	public boolean execute(final String action, final JSONArray data,
			final CallbackContext callbackContext) {
		Log.d(pluginName, pluginName + " called with action: " + action
				+ " and with options: " + data);
		boolean result = false;

		this.callbackContext = callbackContext;

		if (action.equals("show")) {
			this.show(data, callbackContext);
		} else if (action.equals("hide")) {
			this.hide(data, callbackContext);
		} else if (action.equals("isShowing")) {
			this.isShowing(data, callbackContext);
		}

		result = true;

		return result;
	}

	public synchronized void show(final JSONArray data,
			final CallbackContext callbackContext) {
		setDefaultValues();
		readParametersFromData(data);

		showAlert();
	}

	public synchronized void hide(final JSONArray data,
			final CallbackContext callbackContext) {
		if (mHUDDialog != null) {
			mHUDDialog.cancel();
			mHUDDialog = null;
		}
	}

	public synchronized void isShowing(final JSONArray data,
			final CallbackContext callbackContext) {
		if (mHUDDialog != null) {
			if (mHUDDialog.isShowing()) {
				jsSendHUDVisbilitiy(true);
				return;
			}
		}
		jsSendHUDVisbilitiy(false);
	}

	private void setDefaultValues() {
		mText = "";
		mIcon = "";
		mTimeOut = 0;
	}

	private void readParametersFromData(JSONArray data) {
		try {
			JSONObject obj = data.getJSONObject(0);
			if (obj.has(ARG_TEXT)) {
				mText = obj.getString(ARG_TEXT);
			}
			if (obj.has(ARG_ICON)) {
				mIcon = obj.getString(ARG_ICON);
			}
			if (obj.has(ARG_TIME_OUT)) {
				mTimeOut = obj.getInt(ARG_TIME_OUT);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void showAlert() {
		mHUDDialog = new HUDDialog(this.cordova.getActivity(), mText, mIcon,
				mTimeOut);
		mHUDDialog.show();
		mHUDDialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	/* JS */
	private void jsSendHUDVisbilitiy(boolean isVisibile) {
		String visible;
		if (isVisibile) {
			visible = "visible";
		} else {
			visible = "invisible";
		}

		callbackContext.success(visible);
	}
}
