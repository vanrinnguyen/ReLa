package com.navigate.reminderlazier;

import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.navigate.reminderlazier.utils.AppService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountdownActivity extends AppCompatActivity {

    @BindView(R.id.imvAlarm)
    ImageView imvHeader;
    @BindView(R.id.txt_demotext)
    TextView txtDemoText;
//    private TextView txtSpeechInput;
    private Button btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String demo = "i scream and you scream we all scream for ice cream";
//    private String demo = "hello hello hello";
    public static Context appInstance;
    public static int appState = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        appInstance = this;
        ButterKnife.bind(this);
        getDataInit();
        txtDemoText.setText(demo);

        Glide.with(CountdownActivity.this)
                .load(R.drawable.alarm)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(imvHeader));

//        txtSpeechInput = findViewById(R.id.txtSpeechInput);
        btnSpeak = findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                appState = 0;
                promptSpeechInput();
            }
        });

        turnOnNotification();
    }

    @Override
    public void onBackPressed(){
        Log.d("testbutton","back");
        // do something here and don't write super.onBackPressed()
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

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        appState = 0;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Let's read aloud: "+demo);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(getApplicationContext(),
                            result.get(0),
                            Toast.LENGTH_SHORT).show();
                    if (!demo.equals(result.get(0))){
                        appState = 1;
                    }
                }
                break;
            }

        }
    }

    private void turnOnNotification() {
        Intent i = new Intent(this, AppService.class);
        i.putExtra("duration", (long) 60000);
        Log.d("ringtone",ringtone+"");
        if (ringtone != -1) {
            i.putExtra("tone", listRingtone.get(ringtone));
        }
        startService(i);
    }

    private List<Integer> listRingtone = new ArrayList<>();
    private int ringtone = -1;
    private void getDataInit(){
        ringtone = getIntent().getIntExtra("ringtone",-1);

        listRingtone.add(R.raw.lac_troi);
        listRingtone.add(R.raw.ppap);
        listRingtone.add(R.raw.fart);
        listRingtone.add(R.raw.sieu_nhan_gao);
        listRingtone.add(R.raw.dien_may_xanh);
        listRingtone.add(R.raw.harlem_shake);
        ringtone = new Random().nextInt(listRingtone.size());
    }
}
