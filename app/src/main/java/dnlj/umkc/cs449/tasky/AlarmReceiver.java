package dnlj.umkc.cs449.tasky;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MainActivity.notificationChannelId)
			.setSmallIcon(R.drawable.button_calendar)
			.setContentTitle(intent.getStringExtra("text"));
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
	}
}
