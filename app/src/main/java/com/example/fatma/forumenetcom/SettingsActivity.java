package com.example.fatma.forumenetcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class SettingsActivity extends AppCompatActivity {

     private Toolbar mToolbar;


    private DatabaseReference mUserDatabase;

    private FirebaseUser mCurrentUser;

    private Button GetQr;
    private ImageView Qr_image;

    //prog bar
    private ProgressDialog mProgressDialog;


    private TextView mName;
    private MultiFormatWriter multi  = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //ToolBar
        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        GetQr= (Button) findViewById(R.id.generate_qr);
        Qr_image= (ImageView) findViewById(R.id.qr_image);
        mName=(TextView) findViewById(R.id.Affich_name);







        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);
        mUserDatabase.keepSynced(true);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                mName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        GetQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                BitMatrix bitmatrix = multi.encode(current_uid, BarcodeFormat.QR_CODE,300,300);
                    BarcodeEncoder encode = new BarcodeEncoder();
                    Bitmap bitmap = encode.createBitmap(bitmatrix);
                    Qr_image.setImageBitmap(bitmap);
                }
                catch(WriterException e){
                    e.printStackTrace();

                }


            }
        });







    }

}
