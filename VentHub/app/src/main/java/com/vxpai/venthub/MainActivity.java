package com.vxpai.venthub;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.vxpai.entity.ShitListItem;
import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.HttpUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements OnFragmentInteractionListener {

    private static final String SEARCHES = "users";
    private SharedPreferences savedSearches;
    private FragmentManager fm;
    private SlidingMenu slidingMenu;
    private boolean isHomePage = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();


        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);

        LoginFragment.getInstance().setSavedSearches(savedSearches);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, LoginFragment.getInstance())
          .commit();
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setBehindContentView(R.layout.menu_layout);
//        setContentView(R.layout.activity_main);
//
//        fm = getFragmentManager();
//
//
//        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);
//
//        LoginFragment.getInstance().setSavedSearches(savedSearches);
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.container, LoginFragment.getInstance())
//          .commit();
//
//        slidingMenu = getSlidingMenu();
//        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
//        slidingMenu.setBehindWidth(80);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.id_menu_container, PersonalFragment.getInstance()).commit();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendShits(boolean isHomePage) {
        this.isHomePage = isHomePage;
        //MainFragment.getInstance().setWhetherExit(false);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, SendShitsFragment.getInstance())
          .addToBackStack(null)
          .commit();
    }

    @Override
    public void onGoBackToMain() {
//        MainFragment.getInstance().setContentVisibility(true);
//        MainFragment.getInstance().setWhetherExit(true);
//        MainFragment.getInstance().setHomePage(isHomePage);
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.container, MainFragment.getInstance());
//        ft.commit();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

    @Override
    public void onLogin(String email,String password) {
        MainFragment.getInstance().setWhetherExit(true);
        final String fEmail = email;
        final String fPassword = password;

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                pairList.add(new BasicNameValuePair("email", fEmail));
                pairList.add(new BasicNameValuePair("password", fPassword));
                JSONObject json = HttpUtil.Post("http://tucao.vxpai.com/checkuserinfo", pairList);
                try {
                    int status = json.getInt("status");
                    if (status == 0) {
                        SharedPreferences.Editor editor = savedSearches.edit();
                        editor.putString("email", fEmail);
                        editor.putString("password", fPassword);
                        fm.beginTransaction().replace(R.id.container, MainFragment.getInstance()).commit();
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, getString(R.string.wrong_email_password), Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


        @Override
    public void onShowDetailShits(ShitListItem shit) {
        //MainFragment.getInstance().setWhetherExit(false);
        DetailShitsFragment.getInstance().setDetailShit(shit);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, DetailShitsFragment.getInstance())
          .addToBackStack(null)
          .commit();
    }

    @Override
    public void onNewUser() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, RegisterFragment.getInstance())
          .addToBackStack(null)
          .commit();
    }

    @Override
    public void onGoBackToLogin() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, LoginFragment.getInstance());
        ft.commit();
    }

    @Override
    public void onEditPersonalData() {
        MainFragment.getInstance().setWhetherExit(false);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, EditPersonalDataFragment.getInstance());
        ft.commit();
        MainFragment.getInstance().setContentVisibility(false);
    }

    /**
     * 回调接口
     * @author zhaoxin5
     *
     */
    public interface MyTouchListener
    {
        public void onTouchEvent(MotionEvent event);
    }

    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener)
    {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener)
    {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
