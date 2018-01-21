package com.navigate.reminderlazier.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.navigate.reminderlazier.CountdownActivity;
import com.navigate.reminderlazier.MainActivity;
import com.navigate.reminderlazier.R;

/**
 * Created by LazyMonster on 07/01/2017.
 */

public class AppService extends IntentService {

    public static final int STATE_LOCKED = 0;
    public static final int STATE_UNLOCKED = 1;

    private MediaPlayer mPlayer;

    public AppService() {
        super("AppService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int toneId = intent.getIntExtra("tone", R.raw.ringtone);
        mPlayer = MediaPlayer.create(CountdownActivity.appInstance, toneId);
        mPlayer.setLooping(true);

        // loop until time is up
        long startTime = System.currentTimeMillis();
        long duration = intent.getLongExtra("duration", 0); // time in miniseconds
        while (true) {
            if (CountdownActivity.appState == 1) {
                AudioManager mgr = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                int valuess = 9;//range(0-15)
                mgr.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);

                // app is not opening now
                // alert sound
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                }

                // send alert to other team members

            } else {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
