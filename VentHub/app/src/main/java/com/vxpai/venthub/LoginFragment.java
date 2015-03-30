package com.vxpai.venthub;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vxpai.entity.User;
import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.ImageUtil;
import com.vxpai.Adapter.LoggedonUserAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
    public class LoginFragment extends Fragment {

    private static final String SEARCHES = "users";
    private SharedPreferences savedSearches;

    private ImageView mUserImage, mShowUsers, mClearUsername;
    private EditText mUsername, mPassword;
    private Button mLoginBtn;

    private TextView mNewUser, mForgetPwd;

    private PopupWindow mPopupWindow;
    private List<User> mUserlist;
    private View mPopupView;

    private static LoginFragment mInstance;
    private OnFragmentInteractionListener mListener;

    private LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (LoginFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new LoginFragment();
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
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mUserImage = (ImageView)view.findViewById(R.id.id_user_image);

        mShowUsers = (ImageView)view.findViewById(R.id.id_show_users);
        mShowUsers.setOnClickListener(showUsersListener);

        mClearUsername = (ImageView)view.findViewById(R.id.id_clear_text);
        mClearUsername.setOnClickListener(clearUsernameListener);

        mUsername = (EditText)view.findViewById(R.id.id_username);
        mPassword = (EditText)view.findViewById(R.id.id_password);

        mLoginBtn = (Button)view.findViewById(R.id.id_login);
        mLoginBtn.setOnClickListener(loginListener);

        mNewUser = (TextView)view.findViewById(R.id.id_new_user);
        mNewUser.setOnClickListener(newUserListener);

        mForgetPwd = (TextView)view.findViewById(R.id.id_forget_pwd);
        mForgetPwd.setOnClickListener(forgetPwdListener);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.battlefield);
        mUserImage.setImageBitmap(ImageUtil.createCircleImage(bmp, bmp.getWidth()));
        if(!bmp.isRecycled())
            bmp.recycle();

        mUserlist = new ArrayList<User>();

        initUsers();
        initPopupWindow();
    }

    private View.OnClickListener showUsersListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(mPopupWindow == null){
                mPopupWindow = new PopupWindow(mPopupView, mUsername.getWidth() - 4, LinearLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
                mPopupWindow.showAsDropDown(mUsername, 2, 0);
            }else{
                mPopupWindow.showAsDropDown(mUsername, 2, 0);
            }
        }
    };

    private View.OnClickListener clearUsernameListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            mListener.onLogin();
//            User user = new User();
//            user.setImagePath("");
//            user.setUsername("Mike");
//
//            JSONObject obj = new JSONObject();
//            try {
//                obj.put("path", user.getImagePath());
//                obj.put("username", user.getUsername());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            SharedPreferences.Editor preferencesEditor = savedSearches.edit();
//            preferencesEditor.putString("user1", obj.toString()); // store current search
//            preferencesEditor.putString("user2", obj.toString()); // store current search
//            preferencesEditor.putString("user3", obj.toString()); // store current search
//            preferencesEditor.putString("user4", obj.toString()); // store current search
//            preferencesEditor.putString("user5", obj.toString()); // store current search
//            preferencesEditor.putString("user6", obj.toString()); // store current search
//            preferencesEditor.putString("user7", obj.toString()); // store current search
//            preferencesEditor.putString("user8", obj.toString()); // store current search
//            preferencesEditor.putString("user9", obj.toString()); // store current search
//            preferencesEditor.putString("user10", obj.toString()); // store current search
//            preferencesEditor.apply(); // store the updated preferences
        }
    };

    private View.OnClickListener newUserListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onNewUser();
        }
    };

    private View.OnClickListener forgetPwdListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    private void initUsers(){
        List<String> tags = new ArrayList<String>(savedSearches.getAll().keySet());
        for(int i = 0;i < tags.size();i++){
            String json = (String)savedSearches.getAll().get(tags.get(i));
            try {
                JSONObject obj = new JSONObject(json);
                mUserlist.add(new User(obj.getString("path"), obj.getString("username")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initPopupWindow(){
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopupView = layoutInflater.inflate(R.layout.showuser_popwindow, null);
        ListView loggedonUsers = (ListView) mPopupView.findViewById(R.id.id_user_list);
        loggedonUsers.setAdapter(new LoggedonUserAdapter(getActivity(), mUserlist));
    }

    public void setSavedSearches(SharedPreferences savedSearches){ this.savedSearches = savedSearches;}
}
