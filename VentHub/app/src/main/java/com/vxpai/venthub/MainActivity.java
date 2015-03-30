package com.vxpai.venthub;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.vxpai.entity.ShitListItem;
import com.vxpai.interfaces.OnFragmentInteractionListener;


public class MainActivity extends Activity implements OnFragmentInteractionListener {

    private static final String SEARCHES = "users";
    private SharedPreferences savedSearches;
    private FragmentManager fm;
    private SlidingMenu slidingMenu;

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
    public void onSendShits() {
        MainFragment.getInstance().setWhetherExit(false);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, SendShitsFragment.getInstance());
        ft.commit();
    }

    @Override
    public void onGoBackToMain() {
        MainFragment.getInstance().setWhetherExit(true);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, MainFragment.getInstance());
        ft.commit();
    }

    @Override
    public void onLogin() {
        MainFragment.getInstance().setWhetherExit(true);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, MainFragment.getInstance())
                .commit();
    }

    @Override
    public void onShowDetailShits(ShitListItem shit) {
        MainFragment.getInstance().setWhetherExit(false);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, DetailShitsFragment.getInstance());
        ft.commit();
    }

    @Override
    public void onNewUser() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, RegisterFragment.getInstance());
        ft.commit();
    }

    @Override
    public void onGoBackToLogin() {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, LoginFragment.getInstance());
        ft.commit();
    }
}
