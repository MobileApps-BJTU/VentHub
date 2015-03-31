package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.ImageUtil;
import com.vxpai.utils.ScreenUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static MainFragment mInstance;
    private ImageView mUserMenu, mHomePageImg, mSendShitsImg, mFriendListImg;
    private LinearLayout mHomePage, mSendShits, mFriendList;
    private FrameLayout mPersonalMenu;
    private TextView mUserName;
    private FragmentManager fm;

    private boolean isHomePageClicked = true;
    private boolean isSendShitsClicked = false;
    private boolean isFriendListClicked = false;

    private View rootView;

    private boolean whetherExit = false;
    private boolean isHomePage = true;

    private OnFragmentInteractionListener mListener;

    private int mScreenWidth;
    private SlidingMenu menu;

    private SharedPreferences savedSearches;

    private MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (MainFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new MainFragment();
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

        mScreenWidth = ScreenUtils.getScreenWidth(getActivity());

        if(menu == null){
            // configure the SlidingMenu
            menu = new SlidingMenu(getActivity());
            menu.setMode(SlidingMenu.LEFT);
            // 设置触摸屏幕的模式
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            // 设置渐入渐出效果的值
            menu.setFadeDegree(0.35f);
            menu.setBehindWidth(mScreenWidth / 5 * 4);
            /**
             * SLIDING_WINDOW will include the Title/ActionBar in the content
             * section of the SlidingMenu, while SLIDING_CONTENT does not.
             */
            menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
            //为侧滑菜单设置布局
            menu.setMenu(R.layout.menu_layout);
            menu.showContent(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.main_fragment, container, false);

            mUserMenu = (ImageView)rootView.findViewById(R.id.id_user_menu_image);
            mHomePageImg = (ImageView)rootView.findViewById(R.id.id_home_page_img);
            mSendShitsImg = (ImageView)rootView.findViewById(R.id.id_send_shits_img);
            mFriendListImg = (ImageView)rootView.findViewById(R.id.id_friend_list_img);

            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.battlefield);
            mUserMenu.setImageBitmap(ImageUtil.createCircleImage(bmp, bmp.getWidth()));
            if(!bmp.isRecycled())
                bmp.recycle();

            mHomePage = (LinearLayout)rootView.findViewById(R.id.id_home_page);
            mSendShits = (LinearLayout)rootView.findViewById(R.id.id_send_shits);
            mFriendList = (LinearLayout)rootView.findViewById(R.id.id_friend_list);

            mHomePage.setOnClickListener(homePageListener);
            mSendShits.setOnClickListener(sendShitsListener);
            mFriendList.setOnClickListener(friendListListener);

            mPersonalMenu = (FrameLayout)rootView.findViewById(R.id.id_user_menu);
            mPersonalMenu.setOnClickListener(personalMenuListener);

            mUserName = (TextView)rootView.findViewById(R.id.id_username_show);
            mUserName.setOnClickListener(noUseListener);

            HomeFragment.getInstance().setSavedSearches(savedSearches);
            HomeFragment.getInstance().setWhetherExit(true);
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.id_main_container, HomeFragment.getInstance());
            ft.commit();

            isHomePageClicked = true;
            isFriendListClicked = false;
            mHomePageImg.setImageResource(R.drawable.home);
            mFriendListImg.setImageResource(R.drawable.friend_o);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

//        if(isHomePage) {
//            HomeFragment.getInstance().setWhetherExit(true);
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.id_main_container, HomeFragment.getInstance());
//            ft.commit();
//
//            isHomePageClicked = true;
//            isFriendListClicked = false;
//            mHomePageImg.setImageResource(R.drawable.home);
//            mFriendListImg.setImageResource(R.drawable.friend_o);
//        }else{
//            FriendListFragment.getInstance().setWhetherExit(true);
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.id_main_container, FriendListFragment.getInstance());
//            ft.commit();
//
//            isHomePageClicked = false;
//            isFriendListClicked = true;
//            mHomePageImg.setImageResource(R.drawable.home_o);
//            mFriendListImg.setImageResource(R.drawable.friend);
//        }

        return rootView;
    }

//    @Override
//    public void onDestroy() {
//        if(!whetherExit) {
//            if(isHomePageClicked) {
//                HomeFragment.getInstance().setWhetherExit(false);
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.remove(HomeFragment.getInstance());
//                ft.commit();
//            }
//            if(isFriendListClicked) {
//                FriendListFragment.getInstance().setWhetherExit(false);
//                FragmentTransaction ft2 = fm.beginTransaction();
//                ft2.remove(FriendListFragment.getInstance());
//                ft2.commit();
//            }
//       }
//        super.onDestroy();
//    }

   @Override
   public void onDestroy() {
        super.onDestroy();

       rootView = null;
   }

    private View.OnClickListener homePageListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isHomePageClicked) {
                FriendListFragment.getInstance().setWhetherExit(false);
                HomeFragment.getInstance().setWhetherExit(true);
                HomeFragment.getInstance().setSavedSearches(savedSearches);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.id_main_container, HomeFragment.getInstance());
                ft.commit();

                isHomePageClicked = true;
                isSendShitsClicked = false;
                isFriendListClicked = false;

                mHomePageImg.setImageResource(R.drawable.home);
                mFriendListImg.setImageResource(R.drawable.friend_o);
            }
        }
    };

    private View.OnClickListener sendShitsListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isSendShitsClicked) {
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.id_main_container, SendShitsFragment.getInstance());
//                ft.commit();
//
//                isSendShitsClicked = true;
//                isHomePageClicked = false;
//                isFriendListClicked = false;
//
//                mSendShitsImg.setImageResource(R.drawable.favourite);
//                mHomePageImg.setImageResource(R.drawable.home_o);
//                mFriendListImg.setImageResource(R.drawable.friend_o);
                if(isHomePageClicked)
                    mListener.onSendShits(true);
                else
                    mListener.onSendShits(false);

            }
        }
    };

    private View.OnClickListener friendListListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(!isFriendListClicked) {
                HomeFragment.getInstance().setWhetherExit(false);
                FriendListFragment.getInstance().setWhetherExit(true);
                FriendListFragment.getInstance().setSavedSearches(savedSearches);
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.id_main_container, FriendListFragment.getInstance());
                ft.commit();

                isFriendListClicked = true;
                isHomePageClicked = false;
                isSendShitsClicked = false;

                mFriendListImg.setImageResource(R.drawable.friend);
                mHomePageImg.setImageResource(R.drawable.home_o);
            }
        }
    };

    private View.OnClickListener personalMenuListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener noUseListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    public void setWhetherExit(boolean whetherExit){ this.whetherExit = whetherExit; }

    public void setContentVisibility(boolean isShowing){ menu.showContent(isShowing);}

    public void setHomePage(boolean isHomePage) {
        this.isHomePage = isHomePage;
    }

    public void setSavedSearches(SharedPreferences savedSearches){ this.savedSearches = savedSearches;}
}
