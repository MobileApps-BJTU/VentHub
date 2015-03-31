package com.vxpai.venthub;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    private EditText usernameEdit;
    private EditText cur_pwdEdit;
    private EditText new_pwdEdit;
    private EditText con_pwdEdit;
    private EditText dobEdit;
    private EditText provinceEdit;
    private RadioGroup group;
    private String gender;
    private Button saveProfileBtn;


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
            group = (RadioGroup)rootView.findViewById(R.id.radioGroup);
            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup arg0, int arg1) {
                    int radioButtonId = arg0.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton)rootView.findViewById(radioButtonId);  //更新文本内容，以符合选中项
                    gender = rb.getText().toString();
                }
            });
            saveProfileBtn = (Button)rootView.findViewById(R.id.button);
            saveProfileBtn.setOnClickListener(saveProfileListener);

            usernameEdit = (EditText)rootView.findViewById(R.id.editText9);
            cur_pwdEdit = (EditText)rootView.findViewById(R.id.editText7);
            new_pwdEdit = (EditText)rootView.findViewById(R.id.editText2);
            con_pwdEdit = (EditText)rootView.findViewById(R.id.editText3);
            dobEdit = (EditText)rootView.findViewById(R.id.editText5);
            provinceEdit = (EditText)rootView.findViewById(R.id.editText6);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        return rootView;
    }

    private View.OnClickListener saveProfileListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            String username = usernameEdit.getText().toString();
            String cur_pwd = cur_pwdEdit.getText().toString();
            String new_pwd = new_pwdEdit.getText().toString();
            String con_pwd = con_pwdEdit.getText().toString();
            String dob = dobEdit.getText().toString();
            String province = provinceEdit.getText().toString();

            if (new_pwd.equals(con_pwd))
                mListener.onEditProfile(username,new_pwd,dob,province,gender);
        }
    };


    private View.OnClickListener backToMainListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onGoBackToMain();
        }
    };
}
