package com.example.fatma.forumenetcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText input_email;
    private Button resetBtn;
    private Button forgotBackBtn;
    private LinearLayout activity_forgot;
    private TextView msg_sent;
    private ProgressDialog mLogProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        //prog bar
        mLogProgress= new ProgressDialog(this);


        input_email = (EditText) findViewById(R.id.forgetEmailLogin);
        msg_sent = (TextView) findViewById(R.id.msg_sent_text);
        resetBtn = (Button) findViewById(R.id.reset_btn);
        forgotBackBtn = (Button) findViewById(R.id.forgot_back_btn);
        activity_forgot = (LinearLayout) findViewById(R.id.forgot_pwd);

        //init Firebase
        mAuth =FirebaseAuth.getInstance();




        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(input_email.getText().toString())){


                    resetPassword(input_email.getText().toString());
                    mLogProgress.setTitle("Sending Email");
                    mLogProgress.setMessage("Please wait while we send an email to reset your password..");
                    mLogProgress.setCanceledOnTouchOutside(false);
                    mLogProgress.show();
                }
            }
        });


        forgotBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backintent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(backintent);
            }
        });
    }


    private void resetPassword(final String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(activity_forgot,"We have sent password instructions to: "+email,Snackbar.LENGTH_LONG);
                    snackbar.show();
                    input_email.setText("");
                    msg_sent.setText("Please check your Email adress to reset your password");
                    mLogProgress.dismiss();

                }else{
                    Snackbar snackbar = Snackbar.make(activity_forgot,"Failed to send password instructions",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    mLogProgress.dismiss();
                }
            }
        });
    }
}
