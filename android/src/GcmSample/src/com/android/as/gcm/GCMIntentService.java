package com.android.as.gcm;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	private static final String TAG = "GCMSample::Service";

	public static final String SENDER_ID = "903650199715";
	
	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		// 登録完了
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		// 登録解除完了
		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent data) {
		// メッセージ受信
		String str = data.getStringExtra("message");
		Log.i(TAG, "onMessage: msg=" + str);

		NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

		Notification notification = new Notification.Builder(getApplicationContext())
				.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(System.currentTimeMillis())
				.setContentTitle("GCMSample")
				.setContentText(str)
				.setContentIntent(contentIntent)
				.getNotification();
		manager.notify(R.string.app_name, notification);

		{
			// ScreenをONにする
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			Log.i(TAG, "onMessage: Acquiring wakelock for SCREEN_ON");
			mWakelock.acquire();
			
			// 数秒で解除
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					Log.i(TAG, "onMessage: Releasing wakelock for SCREEN_ON");
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		// エラー
		Log.e(TAG, "onError: errorId=" + errorId);
	}

}
