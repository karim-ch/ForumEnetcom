package com.example.fatma.forumenetcom;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SponsorsFragment extends Fragment {


    private View mMainView1;

    public SponsorsFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView1 = inflater.inflate(R.layout.fragment_sponsors, container, false);

        return mMainView1;
    }




}

