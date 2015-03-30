package com.vxpai.venthub;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {


    private static PersonalFragment mInstance;
    private View rootView;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.personal_menu, container, false);

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        return rootView;
    }


}
