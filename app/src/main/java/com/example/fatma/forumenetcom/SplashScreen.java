package com.example.fatma.forumenetcom;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;
    private boolean emailVerified=false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();


                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser==null){
                    Intent Homeintent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(Homeintent);
                    finish();}

                else {
                    emailVerified = currentUser.isEmailVerified();
                    if (emailVerified) {
                        Intent Homeintent = new Intent(SplashScreen.this, MemberActivity.class);
                        startActivity(Homeintent);
                        finish();
                    } else {
                        Intent Homeintent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(Homeintent);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME_OUT);


    }
}