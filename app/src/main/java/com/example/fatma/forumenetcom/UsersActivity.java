package com.example.fatma.forumenetcom;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

        private static int B;

    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;
    public DatabaseReference mCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        //ToolBar
        mToolbar = (Toolbar) findViewById(R.id.users_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Members");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Recycler View
        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));


        //Send a Query to the database
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mCount = FirebaseDatabase.getInstance().getReference().child("noneusers");
        mUsersDatabase.keepSynced(true);


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UsersValues, UsersViewHolder> firebaseRecylerAdapter = new FirebaseRecyclerAdapter<UsersValues, UsersViewHolder>(

                UsersValues.class,
                R.layout.users_single_layout,
                UsersViewHolder.class,
                mUsersDatabase


        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, UsersValues model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setPosition(model.getPosition());
                viewHolder.setWorkshop(model.getWorkshop());

            }
        };

        mUsersList.setAdapter(firebaseRecylerAdapter);


    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        }



        public void setName(String name){
            TextView userNameView =(TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }
        public void setPosition(String position){

            ImageView mIconView =(ImageView) mView.findViewById(R.id.user_single_icon);
            if(position.equals("organisateur")){
                mIconView.setImageResource(R.drawable.star_logo);}
            else  if(position.equals("enseignant")){
                mIconView.setImageResource(R.drawable.teacher_logo);}
            else  if(position.equals("invite")){
                mIconView.setImageResource(R.drawable.speaker_icon);}
            else  if(position.equals("admin")){
                mIconView.setImageResource(R.drawable.administrator_icon);}
            else  if(position.equals("dev")){
                mIconView.setImageResource(R.drawable.dev_icon);}

                else { mIconView.setImageResource(R.drawable.user_icon);}
                }


        public void setWorkshop(String workshop){
            TextView userWorkshopView =(TextView) mView.findViewById(R.id.user_single_workshops);
            userWorkshopView.setText(workshop);}



        }



}







