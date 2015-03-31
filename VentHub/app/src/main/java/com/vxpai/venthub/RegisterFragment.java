package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vxpai.interfaces.OnFragmentInteractionListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    private EditText emailEdit;
    private EditText usernameEdit;
    private EditText pwdEdit;
    private EditText confirmpwdEdit;
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
            emailEdit = (EditText) rootView.findViewById(R.id.editText8);
            usernameEdit = (EditText) rootView.findViewById(R.id.editText10);
            pwdEdit = (EditText) rootView.findViewById(R.id.editText11);
            confirmpwdEdit = (EditText) rootView.findViewById(R.id.editText12);
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

    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }
    private View.OnClickListener registerListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            //TO DO

            String email = emailEdit.getText().toString();
            String username = usernameEdit.getText().toString();
            String pwd = pwdEdit.getText().toString();
            String confirmpwd = confirmpwdEdit.getText().toString();
            //mListener.onRegister(email, username, pwd);
            if (pwd.equals(confirmpwd)) {
                if (isEmail(email))
                    mListener.onRegister(email, username, pwd);
                else {
                    //Looper.prepare();
                    Toast.makeText(getActivity(), getString(R.string.wrong_email_pattern), Toast.LENGTH_LONG).show();
                    //Looper.loop();
                }
            }
            else {
                //Looper.prepare();
                Toast.makeText(getActivity(), getString(R.string.wrong_repeat_password), Toast.LENGTH_LONG).show();
                //Looper.loop();
            }
        }
    };
}
