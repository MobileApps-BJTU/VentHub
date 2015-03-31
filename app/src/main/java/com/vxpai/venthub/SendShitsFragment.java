package com.vxpai.venthub;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vxpai.interfaces.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendShitsFragment extends Fragment {

    private static SendShitsFragment mInstance;
    private OnFragmentInteractionListener mListener;
    private TextView mVentText;
    private CheckBox mCheckBox;

    private SendShitsFragment() {
        // Required empty public constructor
    }

    public static SendShitsFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (SendShitsFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new SendShitsFragment();
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
        View view = inflater.inflate(R.layout.send_fragment,container,false);

        mVentText = (TextView)view.findViewById(R.id.editText);
        mCheckBox = (CheckBox)view.findViewById(R.id.checkBox);

        ImageView back = (ImageView)view.findViewById(R.id.id_backfromshits);
        TextView send = (TextView)view.findViewById(R.id.id_send_shits);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onGoBackToMain();
            }
        });

        send.setOnClickListener(sentVentListener);
        return view;
    }


    private View.OnClickListener sentVentListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String sVent = mVentText.getText().toString();
            boolean isAnnoy = mCheckBox.isChecked();
            mListener.onSendVent(sVent, isAnnoy);
        }
    };
}
