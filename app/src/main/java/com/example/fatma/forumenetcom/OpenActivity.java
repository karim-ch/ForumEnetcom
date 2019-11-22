package com.example.fatma.forumenetcom;

        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ServerValue;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.messaging.FirebaseMessaging;
        import com.squareup.picasso.Picasso;



public class OpenActivity extends AppCompatActivity {

    private Button btn_register_workshop;
    private ImageView mOpenImage;
    private TextView mOpenTitle,mOpenPlace,mOpenDate,mOpenDescrip;
    private ImageView mVerif;
    private DatabaseReference mOpenDatabase;
    private FirebaseUser mCurrent_User;
    private FirebaseAuth mAuth;

    private boolean mProcessLike=false;
    private boolean mProcessSeen=false;

    private DatabaseReference mUsersWorkshops;

    private String disponibilite;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        mAuth=FirebaseAuth.getInstance();

        btn_register_workshop=(Button)findViewById(R.id.btn_register_workshop);

        mOpenImage = (ImageView) findViewById(R.id.open_activity_image);
        mOpenPlace = (TextView) findViewById(R.id.open_activity_place);
        mOpenDate = (TextView) findViewById(R.id.open_activity_date);
        mOpenDescrip = (TextView) findViewById(R.id.open_activity_descrip);
        mOpenTitle = (TextView) findViewById(R.id.open_activity_title);


        mVerif =(ImageView) findViewById(R.id.seen_post);



        mCurrent_User= FirebaseAuth.getInstance().getCurrentUser();

        mUsersWorkshops= FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid());

        final String news_id =  getIntent().getStringExtra("news_id");

        mOpenDatabase= FirebaseDatabase.getInstance().getReference().child("program").child(news_id);



        //-----------------------Participate FONCT--------------------------------------------------------------
        btn_register_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                mUsersWorkshops.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       String dispo = dataSnapshot.child("workshop").getValue().toString();
                       if (disponibilite.equals("1")) {
                            if (dispo.equals("none")) {

                                mUsersWorkshops.child("workshop").setValue(news_id);
                                FirebaseMessaging.getInstance().subscribeToTopic(news_id);
                                btn_register_workshop.setText("Unregister");
                            } else if (dispo.equals(news_id)) {
                                mUsersWorkshops.child("workshop").setValue("none");
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(news_id);
                                btn_register_workshop.setText("Register");
                            } else {
                                Toast.makeText(OpenActivity.this, "You are already registered to the workshop " + dispo, Toast.LENGTH_SHORT).show();
                            }
                       }else if (disponibilite.equals("0")){
                           Toast.makeText(OpenActivity.this , "Registration will be available soon!", Toast.LENGTH_LONG).show();
                       }else {Toast.makeText(OpenActivity.this , "Registration is closed!", Toast.LENGTH_LONG).show();

                    }
                       }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });
//-------------------------------------------------------------------------------------------



        mOpenDatabase.keepSynced(true);


        mOpenDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String display_title = dataSnapshot.child("title").getValue().toString();
                String display_date = dataSnapshot.child("date").getValue().toString();
                String display_place = dataSnapshot.child("place").getValue().toString();
                String display_descrip = dataSnapshot.child("descrip").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                disponibilite = dataSnapshot.child("dispo").getValue().toString();

                mOpenTitle.setText(display_title);
                mOpenDate.setText(display_date);
                mOpenPlace.setText(display_place);
                mOpenDescrip.setText(display_descrip);
                Picasso.with(OpenActivity.this).load(image).placeholder(R.drawable.enetcom).into(mOpenImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mUsersWorkshops.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dispo = dataSnapshot.child("workshop").getValue().toString();
                if(dispo.equals(news_id))
                {
                    mVerif.setImageResource(R.drawable.verfi_blue);
                    btn_register_workshop.setText("Unregister");
                }else{
                    mVerif.setImageResource(R.drawable.verfi);
                    btn_register_workshop.setText("Register");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

