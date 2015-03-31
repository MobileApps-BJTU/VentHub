package com.vxpai.venthub;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


import com.vxpai.Adapter.FriendListAdapter;
import com.vxpai.entity.CommentListItem;
import com.vxpai.entity.ShitListItem;
import com.vxpai.entity.UserData;
import com.vxpai.entity.UserListItem;
import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.HttpUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class FriendListFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;

    private static FriendListFragment mInstance;

    private boolean whetherExit = false;

    private List<UserListItem> list;

    private FriendListAdapter friendListAdapter;

    private SharedPreferences savedSearches;

    private FriendListFragment() {
        // Required empty public constructor
    }

    public static FriendListFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (FriendListFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new FriendListFragment();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<UserListItem>();
        friendListAdapter = new FriendListAdapter(getActivity(),list);
        setListAdapter(friendListAdapter);
//        list.add(new UserListItem(new UserData("Mike@163.com", "Mike", "1234")));
//        list.add(new UserListItem(new UserData("Ann@163.com", "Ann", "1234")));
//        list.add(new UserListItem(new UserData("Paul@163.com", "Paul", "1234")));
//        list.add(new UserListItem(new UserData("Peter@163.com", "Peter", "1234")));
//        list.add(new UserListItem(new UserData("Brown@163.com", "Brown", "1234")));
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                pairList.add(new BasicNameValuePair("email", savedSearches.getString("email", null)));
                pairList.add(new BasicNameValuePair("password",savedSearches.getString("password", null)));

                JSONArray json = HttpUtil.PostArray("http://tucao.vxpai.com/getfollowlist", pairList);
                for(int i = 0;i < json.length();i++) {
                    try {
                        JSONArray obj = new JSONArray(json.get(i).toString());
                        list.add(new UserListItem(new UserData((String)obj.get(0),(String)obj.get(1),null)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tt.start();
        try {
            tt.join();
            friendListAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.friendlist_fragment,container,false);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }
        return rootView;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onShowDetailUser(list.get(position).getUserData());
        }
    }

    public void setWhetherExit(boolean whetherExit){ this.whetherExit = whetherExit; }

    public void setSavedSearches(SharedPreferences savedSearches) {
        this.savedSearches = savedSearches;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
