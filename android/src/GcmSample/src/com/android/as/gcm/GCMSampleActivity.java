package com.android.as.gcm;

import com.google.android.gcm.GCMRegistrar;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

public class GCMSampleActivity extends Activity {
	
	private static final String TAG = "GCMSample::Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// デバイス・マニフェストの確認
		GCMRegistrar.checkDevice(getApplicationContext());
		GCMRegistrar.checkManifest(getApplicationContext());
		// 登録済みかどうかを判別
		String regId = GCMRegistrar.getRegistrationId(getApplicationContext());
		if (TextUtils.isEmpty(regId)) {
			// 未登録
			Log.i(TAG, "未登録");
			GCMRegistrar.register(getApplicationContext(), GCMIntentService.SENDER_ID);
		} else {
			// 登録済み
			Log.i(TAG, "登録済み");
		}
	}
}
