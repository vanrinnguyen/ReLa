package com.navigate.reminderlazier;

import android.app.KeyguardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

public class CountdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
    }

    @Override
    public void onBackPressed(){
        Log.d("testbutton","back");
        // do something here and don't write super.onBackPressed()
    }

//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//    }
//
//    public void onUserLeaveHint() { // this only executes when Home is selected.
//        // do stuff
//        super.onUserLeaveHint();
//        System.out.println("HOMEEEEEEEEE");
//    }

//    @Override
//    public void onAttachedToWindow()
//    {
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//        super.onAttachedToWindow();
//    }

    @Override
    public void onAttachedToWindow()
    {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Log.d("testbutton","backEventcode");
                // do something here
                return true;
            case KeyEvent.KEYCODE_HOME:
                Log.d("testbutton","home");
                // do something here
                return true;
            case KeyEvent.KEYCODE_NUMPAD_MULTIPLY:
                Log.d("testbutton","multi");
                // do something here
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                Log.d("testbutton","backEventcode");
                // do something here
                return true;
            case KeyEvent.KEYCODE_HOME:
                Log.d("testbutton","home");
                // do something here
                return true;
            case KeyEvent.KEYCODE_NUMPAD_MULTIPLY:
                Log.d("testbutton","multi");
                // do something here
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
