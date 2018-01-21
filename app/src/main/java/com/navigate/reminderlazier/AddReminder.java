package com.navigate.reminderlazier;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.navigate.reminderlazier.fragments.TimePickerFragment;
import com.navigate.reminderlazier.utils.ContentSupplier;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddReminder extends AppCompatActivity {
    EditText edtReminderName, edtLocation, edtFriendEmail;
    TextView txtDate, txtTime;
    Button btnDone;
    ImageView imvHeader;
    Calendar date = Calendar.getInstance();
    String TAG = "AddReminder";
    ContentSupplier contentSupplier = new ContentSupplier();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar();
        setContentView(R.layout.activity_add_reminder);
        setControl();
        Glide.with(AddReminder.this)
                .load(R.drawable.running)
                .crossFade()
                .into(new GlideDrawableImageViewTarget(imvHeader));
        handleEvent();
    }

    private void handleEvent() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClickBtnDone();
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClickTxtDate();
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnClickTxtTime();
            }
        });
    }

    private void handleOnClickTxtDate() {
        final Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                txtDate.setText(year + "/" + monthOfYear + 1 + "/" + dayOfMonth);
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void handleOnClickTxtTime() {
        final Calendar currentDate = Calendar.getInstance();
        new TimePickerDialog(AddReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                txtTime.setText(hourOfDay+":"+minute);
            }
        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
    }

    private void handleOnClickBtnDone() {
        SharedPreferences pre = getSharedPreferences ("email_user",MODE_PRIVATE);
        String creator = pre.getString("username", "");
        Log.d(TAG, "creator: " + creator);
        String lstEmail = edtFriendEmail.getText().toString();
        String[] userEmails = lstEmail.split(";");
        String reminderName = edtReminderName.getText().toString();
        String location = edtLocation.getText().toString();
        String unixTime = String.valueOf(date.getTimeInMillis());
        Long currentDate = new Date().getTime();
        String currentTime = currentDate.toString();
        Log.d(TAG, currentTime);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data");
        String regex = "[^\\d\\w]";
        for (String userEmail : userEmails) {
            Log.d(TAG,"email friends:" + userEmails.length);
            String inputChild = contentSupplier.parseText(userEmail, regex);
            myRef.child(inputChild).child(currentTime).child("creator").setValue(creator);
            myRef.child(inputChild).child(currentTime).child("reminderName").setValue(reminderName);
            myRef.child(inputChild).child(currentTime).child("unixTime").setValue(unixTime);
            myRef.child(inputChild).child(currentTime).child("location").setValue(location);
        }

        String inputChild = contentSupplier.parseText(creator, regex);
        myRef.child(inputChild).child(currentTime).child("unixTime").setValue(unixTime);
        myRef.child(inputChild).child(currentTime).child("creator").setValue(creator);
        myRef.child(inputChild).child(currentTime).child("reminderName").setValue(reminderName);
        myRef.child(inputChild).child(currentTime).child("location").setValue(location);
        if (userEmails.length > 0) {
            SharedPreferences preFriends = getSharedPreferences("alarm_friend", MODE_PRIVATE);
            SharedPreferences.Editor edit=preFriends.edit();
            Log.d(TAG, userEmails.toString());
            String name = "";
            for (String userEmail : userEmails) {
                name = name + userEmail + ";";
            }
            edit.putString(currentTime, name);
            edit.commit();
        }
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Value is: " + dataSnapshot.getValue());
                finish();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void setControl() {
        edtReminderName = findViewById(R.id.edtReminderName);
        edtLocation = findViewById(R.id.edtLocation);
        edtFriendEmail = findViewById(R.id.edtFriendEmail);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        btnDone = findViewById(R.id.btnDone);
        imvHeader = findViewById(R.id.imvHeader);
    }
}
