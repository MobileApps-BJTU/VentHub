package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vxpai.interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    private static RegisterFragment mInstance;
    private FragmentManager fm;
    private OnFragmentInteractionListener mListener;
    private ImageView backToLogin;
    private Button registerBtn;

    private View rootView;

    private RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (RegisterFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RegisterFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.register_fragment, container, false);
            backToLogin = (ImageView)rootView.findViewById(R.id.id_back_to_login);
            registerBtn = (Button)rootView.findViewById(R.id.id_register);

            backToLogin.setOnClickListener(backToLoginListener);
            registerBtn.setOnClickListener(registerListener);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }



        return rootView;
    }

    private View.OnClickListener backToLoginListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onGoBackToLogin();
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            //TO DO
            mListener.onGoBackToLogin();
        }
    };
}
