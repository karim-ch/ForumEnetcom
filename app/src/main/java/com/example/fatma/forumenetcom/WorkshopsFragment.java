package com.example.fatma.forumenetcom;




import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;


public class WorkshopsFragment extends Fragment {

    private RecyclerView mWorkshopsList;
    private DatabaseReference mWorkshopsDatabase;



    private FirebaseAuth mAuth;

    private View mMainView;

    public WorkshopsFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_workshops, container, false);
        mWorkshopsList = (RecyclerView) mMainView.findViewById(R.id.workshops_list);

        mAuth=FirebaseAuth.getInstance();


        mWorkshopsDatabase = FirebaseDatabase.getInstance().getReference().child("program");
        mWorkshopsDatabase.keepSynced(true);


        mWorkshopsList.setHasFixedSize(true);
        mWorkshopsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ProgramValues, ProgramViewHolder> firebaseRecylerAdapter = new FirebaseRecyclerAdapter<ProgramValues, ProgramViewHolder>(

                ProgramValues.class,
                R.layout.program_single_layout,
                ProgramViewHolder.class,
                mWorkshopsDatabase


        ) {
            @Override
            protected void populateViewHolder(final ProgramViewHolder viewHolder, ProgramValues model,final int position) {
                final String list_user_id=getRef(position).getKey();

                mWorkshopsDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String workshop_name=dataSnapshot.child("title").getValue().toString();
                        final String workshop_date=dataSnapshot.child("date").getValue().toString();
                        final String workshop_place=dataSnapshot.child("place").getValue().toString();
                        final String workshop_image=dataSnapshot.child("image").getValue().toString();




                        viewHolder.setName(workshop_name);
                        viewHolder.setDate(workshop_date);
                        viewHolder.setPlace(workshop_place);
                        viewHolder.setImage(workshop_image, getContext());

                        final String news_id = getRef(position).getKey();


                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent workshopIntent = new Intent(getContext(), OpenActivity.class);
                                workshopIntent.putExtra("news_id", news_id);
                                startActivity(workshopIntent);

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        };

        mWorkshopsList.setAdapter(firebaseRecylerAdapter);


    }

    public static class ProgramViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ProgramViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        }





        public void setName(String name){

            TextView Workshop_name =(TextView) mView.findViewById(R.id.news_single_name);
            Workshop_name.setText(name);

        }

        public void setDate(String date){

            TextView Workshop_date =(TextView) mView.findViewById(R.id.news_single_date);
            Workshop_date.setText(date);

        }

        public void setPlace(String place){

            TextView Workshop_place =(TextView) mView.findViewById(R.id.news_single_place);
            Workshop_place.setText(place);

        }


        public void setImage(String image, Context ctx){

            ImageView Workshop_image =(ImageView) mView.findViewById(R.id.news_single_image);
            Picasso.with(ctx).load(image).placeholder(R.drawable.enetcom).into(Workshop_image);


        }



    }

}

