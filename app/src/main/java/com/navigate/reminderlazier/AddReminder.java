package com.navigate.reminderlazier;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.navigate.reminderlazier.fragments.TimePickerFragment;

import java.util.Date;

public class AddReminder extends AppCompatActivity {
    EditText edtReminderName, edtLocation, edtFriendEmail;
    TextView txtDate, txtTime;
    Button btnDone;
    String TAG = "AddReminder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        setControl();
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

    private void handleOnClickTxtTime() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    private void handleOnClickTxtDate() {

    }

    private void handleOnClickBtnDone() {
        String userEmail = "ducviencse";
        String creator = edtFriendEmail.getText().toString();
        String reminderName = edtReminderName.getText().toString();
        String location = edtLocation.getText().toString();
        String unixTime = txtDate.getText().toString() + txtTime.getText().toString();
        Long date = new Date().getTime();
        String currentTime = date.toString();
        Log.d(TAG, currentTime);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("data");

        myRef.child(userEmail).child(currentTime).child("creator").setValue(creator);
        myRef.child(userEmail).child(currentTime).child("reminderName").setValue(reminderName);
        myRef.child(userEmail).child(currentTime).child("unixTime").setValue(unixTime);
        myRef.child(userEmail).child(currentTime).child("location").setValue(location);
        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.d(TAG, "Value is: " + dataSnapshot.getValue());
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
    }
}
