package com.example.fatma.forumenetcom;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;
import java.util.HashMap;

public class Registration extends AppCompatActivity {
    private static final  String TAG = "Registration";
    private Toolbar mToolbar;

    RadioButton radioyes;
    RadioButton radiono;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText email;
    private EditText phone;
    private EditText nickname;
    private EditText password;
    private EditText confirm_password;
    private Button buttonRegister;

    private LinearLayout activity_registration;


    Boolean cond_name = false;
    Boolean cond_email = false;
    Boolean cond_password = false;
    Boolean cond_confirm_password = false;
    Boolean cond_phone = false;

    Boolean btn_clicked = false;


    //progress Dialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;
    //Real Time Database
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        activity_registration = (LinearLayout) findViewById(R.id.registr);


        //ToolBar
        mToolbar = (Toolbar) findViewById(R.id.registration_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create a new Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //scroll click
        findViewById(R.id.registr).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });



        //progressBar
        mRegProgress= new ProgressDialog(this);

        //Firebase instance
        mAuth= FirebaseAuth.getInstance();


        buttonRegister=(Button) findViewById(R.id.buttonRegister);

        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        confirm_password=(EditText) findViewById(R.id.confirm_password);
        nickname=(EditText) findViewById(R.id.nickname);
        phone=(EditText) findViewById(R.id.phone);



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String display_email = email.getText().toString();
                String display_password = password.getText().toString();
                String display_password_confirm = confirm_password.getText().toString();
                String display_phone = phone.getText().toString();
                String display_nickname = nickname.getText().toString();
                if(btn_clicked)
                {
                      Intent MainIntent= new Intent(Registration.this,MainActivity.class);
                                        MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(MainIntent);
                                        finish();
                }
                else{
                 if(display_nickname.length()<3 ){
                    cond_name=false;
                        nickname.setError("Name must contain at least 3 characters");
                } else{ cond_name=true;}


                if(!Patterns.EMAIL_ADDRESS.matcher(display_email).matches()){
                    cond_name=false;
                    email.setError("Invalid email");
                } else{ cond_email=true;}

                if(display_password.length()<6 ){
                    cond_password=false;
                    password.setError("Password must contain at least 6 characters");
                } else{ cond_password=true;}

                if(display_phone.length()!= 8){
                    cond_phone=false;
                    phone.setError("Invalid Phone Number");
                } else{ cond_phone=true;}


                if(!display_password_confirm.equals(display_password)|| display_password_confirm.equals("")){
                    cond_confirm_password=false;
                    confirm_password.setError("Password mismatch");
                } else{ cond_confirm_password=true;}


                if(cond_phone && cond_confirm_password && cond_email && cond_password && cond_name){
                    mRegProgress.setTitle("Registring User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();


                    register_user(display_email, display_password, display_nickname,display_phone);
                    }



            }}

            private void register_user(final String display_email, final String display_password, final String display_nickname,final String display_phone) {

                mAuth.createUserWithEmailAndPassword(display_email, display_password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                            String device_token= FirebaseInstanceId.getInstance().getToken();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(display_nickname).build();

                            current_user.updateProfile(profileUpdates);


                            HashMap<String, String> userMap = new HashMap<String, String>();
                            userMap.put("name",display_nickname);
                            userMap.put("phone",display_phone);
                            userMap.put("device_token",device_token);
                            userMap.put("position","default");
                            userMap.put("pack","0");
                            userMap.put("workshop","none");
                            userMap.put("restauration","0");
                            userMap.put("add","0");


                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        FirebaseUser user = auth.getCurrentUser();

                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Snackbar snackbar = Snackbar.make(activity_registration,"We have sent email confirmation to: "+email.getText().toString(),Snackbar.LENGTH_LONG);
                                                            snackbar.show();

                                                            buttonRegister.setText("Login");
                                                            btn_clicked=true;
                                                            mRegProgress.hide();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });




                        } else{
                            mRegProgress.hide();
                            Toast.makeText(Registration.this, "Cannot Sign Up. Please review the form", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });








    }


}


