package com.vxpai.venthub;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vxpai.interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static PersonalFragment mInstance;
    private View rootView;

    private LinearLayout editPersonalData;


    public PersonalFragment() {
        // Required empty public constructor
    }
//
//    public static PersonalFragment getInstance()
//    {
//
//        if (mInstance == null)
//        {
//            synchronized (PersonalFragment.class)
//            {
//                if (mInstance == null)
//                {
//                    mInstance = new PersonalFragment();
//                }
//            }
//        }
//        return mInstance;
//    }

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
            rootView = inflater.inflate(R.layout.personal_menu, container, false);
            editPersonalData = (LinearLayout)rootView.findViewById(R.id.id_edit_personal_data);
            editPersonalData.setOnClickListener(editPersonalDataListener);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private View.OnClickListener editPersonalDataListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onEditPersonalData();
        }
    };
}
