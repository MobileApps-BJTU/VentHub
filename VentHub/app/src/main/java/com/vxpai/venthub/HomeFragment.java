package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.ImageUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static HomeFragment mInstance;
    private FragmentManager fm;

    private TextView mFriendSquare, mTopList, mAnonymitySquare;
    private boolean isFriendSquareClicked = true;
    private boolean isTopListClicked = false;
    private boolean isAnonymitySquareClicked = false;

    private View rootView;

    private boolean whetherExit = false;

    private HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (HomeFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HomeFragment();
                }
            }
        }
        return mInstance;
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
            rootView = inflater.inflate(R.layout.home_fragment, container, false);

            mFriendSquare = (TextView)rootView.findViewById(R.id.id_friend_square);
            mTopList = (TextView)rootView.findViewById(R.id.id_top_list);
            mAnonymitySquare = (TextView)rootView.findViewById(R.id.id_anonymity_square);

            mFriendSquare.setOnClickListener(friendSquareListener);
            mTopList.setOnClickListener(topListListener);
            mAnonymitySquare.setOnClickListener(anonymitySquareListener);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.id_home_container, FriendFragment.getInstance());
        ft.commit();

        return rootView;
    }

    @Override
    public void onDestroy() {
        if(!whetherExit) {
            if(isFriendSquareClicked) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.remove(FriendFragment.getInstance());
                ft.commit();
            }
            if(isTopListClicked) {
                FragmentTransaction ft2 = fm.beginTransaction();
                ft2.remove(TopFragment.getInstance());
                ft2.commit();
            }
            if(isAnonymitySquareClicked) {
                FragmentTransaction ft3 = fm.beginTransaction();
                ft3.remove(AnonymityFragment.getInstance());
                ft3.commit();
            }
        }

        super.onDestroy();
    }

    private View.OnClickListener friendSquareListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isFriendSquareClicked){
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.id_home_container, FriendFragment.getInstance());
                ft.commit();

                isFriendSquareClicked = true;
                isTopListClicked = false;
                isAnonymitySquareClicked = false;
            }
        }
    };

    private View.OnClickListener topListListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isTopListClicked){
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.id_home_container, TopFragment.getInstance());
                ft.commit();

                isTopListClicked = true;
                isAnonymitySquareClicked = false;
                isFriendSquareClicked = false;
            }
        }
    };

    private View.OnClickListener anonymitySquareListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isAnonymitySquareClicked){
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.id_home_container, AnonymityFragment.getInstance());
                ft.commit();

                isAnonymitySquareClicked = true;
                isFriendSquareClicked = false;
                isTopListClicked = false;
            }
        }
    };

    public void setWhetherExit(boolean whetherExit){ this.whetherExit = whetherExit; }
}
