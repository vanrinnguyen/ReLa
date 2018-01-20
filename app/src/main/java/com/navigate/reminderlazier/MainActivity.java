package com.navigate.reminderlazier;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Importing Google GMS Auth API Libraries.
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    // TAG is for show some tag logs in LOG screen.
    public static final String TAG = "MainActivity";

    // Request sing in code. Could be anything as you required.
    public static final int RC_SIGN_IN = 7;

    // Firebase Auth Object.
    public FirebaseAuth firebaseAuth;

    // Google API Client object.
    public GoogleApiClient googleApiClient;

//    // Sing out button.
//    Button SignOutButton;

    // Google Sign In button .
    com.google.android.gms.common.SignInButton signInButton;

    // TextView to Show Login User Email and Name.
    TextView LoginUserName, LoginUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//
//        SignOutButton= (Button) findViewById(R.id.sign_out);
//
//        LoginUserName = (TextView) findViewById(R.id.textViewName);
//
//        LoginUserEmail = (TextView) findViewById(R.id.textViewEmail);

        signInButton = (com.google.android.gms.common.SignInButton)findViewById(R.id.sign_in_button);

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        // Hiding the TextView on activity start up time.
//        LoginUserEmail.setVisibility(View.GONE);
//        LoginUserName.setVisibility(View.GONE);

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .enableAutoManage(MainActivity.this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();


        // Adding Click listener to User Sign in Google button.
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });

//        // Adding Click Listener to User Sign Out button.
//        SignOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                UserSignOutFunction();
//
//            }
//        });

    }


    // Sign In function Starts From Here.
    public void UserSignInMethod(){
        Log.d(TAG, "started");
        // Passing Google Api Client into Intent.
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "started activity");
        if (requestCode == RC_SIGN_IN){
            Log.d(TAG, "started request");

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()){
                Log.d(TAG, "login");

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }

        }
    }

    public String parseText(String input, String regex) {
       input =  input.replaceAll(regex, "");
       input = input.replace("com","");
        input = input.replace("vn","");
        return input.replace("gmail","");
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

        Toast.makeText(MainActivity.this,""+ authCredential.getProvider(),Toast.LENGTH_LONG).show();
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> AuthResultTask) {
                                if (AuthResultTask.isSuccessful()){

                                    // Getting Current Login user details.
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                                    // Showing Log out button.
//                                    SignOutButton.setVisibility(View.VISIBLE);
//
//                                    // Hiding Login in button.
//                                    signInButton.setVisibility(View.GONE);
//
//                                    // Showing the TextView.
//                                    LoginUserEmail.setVisibility(View.VISIBLE);
//                                    LoginUserName.setVisibility(View.VISIBLE);
//
//                                    // Setting up name into TextView.
//                                    LoginUserName.setText("NAME =  "+ firebaseUser.getDisplayName().toString());
//
//                                    // Setting up Email into TextView.
//                                    LoginUserEmail.setText("Email =  "+ firebaseUser.getEmail().toString());
                                    SharedPreferences pre=getSharedPreferences ("email_user",MODE_PRIVATE);
                                    SharedPreferences.Editor edit=pre.edit();
                                    String userMail = firebaseUser.getEmail().toString();
                                    String regex = "[^\\d\\w]";
                                    String userName = parseText(userMail, regex);
//                                    if (pre.getString("username",null) == null) {
//
//                                    }
                                    edit.putString("username", userName);
                                    edit.commit();

                                    Log.d(TAG, "username: " + userName);

                                    Intent view2 = new Intent(MainActivity.this, View2.class);
                                    startActivity(view2);


                                }else {
                                    Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
    }

//    public void UserSignOutFunction() {
//
//        // Sing Out the User.
//        firebaseAuth.signOut();
//        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
//
//
//        // After logout Hiding sign out button.
//        SignOutButton.setVisibility(View.GONE);
//
//        // After logout setting up email and name to null.
//        LoginUserName.setText(null);
//        LoginUserEmail.setText(null);
//
//        // After logout setting up login button visibility to visible.
//        signInButton.setVisibility(View.VISIBLE);
//    }

}
