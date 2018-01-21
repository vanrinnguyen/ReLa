package com.navigate.reminderlazier.utils;

import android.content.SharedPreferences;
/**
 * Created by BaLinh on 1/21/2018.
 */

public class ContentSupplier {
    public String addSharedPreferences(SharedPreferences pre, String timeSetUp, long timeAlarmUp){
        if (pre.getLong(timeSetUp, 0) == 0) {
            SharedPreferences.Editor edit=pre.edit();
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
