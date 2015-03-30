package com.vxpai.venthub;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vxpai.interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPersonalDataFragment extends Fragment {


    private static EditPersonalDataFragment mInstance;
    private View rootView;
    private OnFragmentInteractionListener mListener;

    private ImageView backToMain;


    private EditPersonalDataFragment() {
        // Required empty public constructor
    }

    public static EditPersonalDataFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (EditPersonalDataFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new EditPersonalDataFragment();
                }
            }
        }
        return mInstance;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.set_userdata_fragment, container, false);
            backToMain = (ImageView)rootView.findViewById(R.id.id_personal_data_back);
            backToMain.setOnClickListener(backToMainListener);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private View.OnClickListener backToMainListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onGoBackToMain();
        }
    };
}
