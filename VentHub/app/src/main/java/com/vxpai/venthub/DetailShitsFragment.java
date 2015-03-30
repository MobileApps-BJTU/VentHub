package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.ImageUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailShitsFragment extends Fragment {

    private static DetailShitsFragment mInstance;
    private FragmentManager fm;
    private View rootView;
    private ImageView backToShits;

    private OnFragmentInteractionListener mListener;

    private DetailShitsFragment() {
        // Required empty public constructor
    }

    public static DetailShitsFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (DetailShitsFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new DetailShitsFragment();
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
            rootView = inflater.inflate(R.layout.detail_shits_container_frament, container, false);
            backToShits = (ImageView)rootView.findViewById(R.id.id_back_to_shits);
            backToShits.setOnClickListener(backToShitsListener);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private View.OnClickListener backToShitsListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onGoBackToMain();
        }
    };
}
