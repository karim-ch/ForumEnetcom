package com.example.fatma.forumenetcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MemberActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private boolean emailVerified= false;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_m);
        setSupportActionBar(toolbar);


        mViewPager = (ViewPager) findViewById(R.id.container_m);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout=(TabLayout) findViewById(R.id.tabs_m);
        mTabLayout.setupWithViewPager(mViewPager);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!= null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());
        }




        }




    //**************
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        emailVerified = currentUser.isEmailVerified();
        if(currentUser == null || !emailVerified ){

            SendToLogin();

        }

    }






    private void SendToLogin() {
        Intent Startintent = new Intent(MemberActivity.this, MainActivity.class);
        Startintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Startintent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_membre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.MembreLogOut) {
            FirebaseAuth.getInstance().signOut();
            SendToLogin();

        }
        if (item.getItemId() == R.id.Account_Settings) {
            Intent SettingsIntent = new Intent(MemberActivity.this, SettingsActivity.class);
            startActivity(SettingsIntent);

        }
        if (item.getItemId() == R.id.All_users) {
            Intent SettingsIntent = new Intent(MemberActivity.this, UsersActivity.class);
            startActivity(SettingsIntent);

        }
        if (item.getItemId() == R.id.Contact_id) {
            Intent SettingsIntent = new Intent(MemberActivity.this, ContactActivity.class);
            startActivity(SettingsIntent);

        }
        return true;

    }
}
