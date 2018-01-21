package com.navigate.reminderlazier.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by BaLinh on 1/21/2018.
 */

public class ContentSupplier {
    public String addSharedPreferences(Context context, SharedPreferences pre, String timeSetUp, long timeAlarmUp, int usecase){
        if (pre.getLong(timeSetUp, 0) == 0) {
            //broadcast
            SharedPreferences.Editor edit=pre.edit();
            if (pre.getString("Alarm running", null) == null && usecase == 1) {
                edit.putString("Alarm running", timeSetUp);
                edit.commit();
            } else {
                AlarmUtils.create(context, timeAlarmUp, 1);
                edit.putString("Alarm running", null);
                edit.commit();
            }
            AlarmUtils.create(context, timeAlarmUp, usecase);



            edit.putLong(timeSetUp, timeAlarmUp);
            edit.commit();
            return "Not Received";
        }
        return "Received";
    }

    public String parseText(String input, String regex) {
        input =  input.replaceAll(regex, "");
        input = input.replace("com","");
        input = input.replace("vn","");
        return input.replace("gmail","");
    }
}
