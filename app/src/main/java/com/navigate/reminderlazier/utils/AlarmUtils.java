package com.navigate.reminderlazier.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.navigate.reminderlazier.service.SchedulingService;

import java.util.Calendar;


/**
 * Created by framgia on 23/05/2017.
 */
public class AlarmUtils {

    public static void create(Context context, long time) {
        Log.d("testAlarm", "hello");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SchedulingService.class);
        intent.putExtra(Constant.KEY_TYPE, time);
        PendingIntent pendingIntent =
                PendingIntent.getService(context, (int)(time/1000), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager
                    .setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            alarmManager
                    .set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }
}
