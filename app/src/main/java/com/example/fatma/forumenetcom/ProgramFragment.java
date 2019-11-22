package com.example.fatma.forumenetcom;

        import android.support.v4.app.Fragment;
        import android.os.Bundle;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;


public class ProgramFragment extends Fragment {


    private View mMainView;

    public ProgramFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_program, container, false);

        return mMainView;
    }




}

