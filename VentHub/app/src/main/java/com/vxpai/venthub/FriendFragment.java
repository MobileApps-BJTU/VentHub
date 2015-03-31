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


import com.vxpai.Adapter.ShitListAdapter;
import com.vxpai.entity.CommentListItem;
import com.vxpai.entity.ReplyListItem;
import com.vxpai.entity.ShitListItem;
import com.vxpai.entity.UserData;
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
public class FriendFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;
    private List<ShitListItem> list = new ArrayList<ShitListItem>();

    private static FriendFragment mInstance;

    private SharedPreferences savedSearches;

    private ShitListAdapter shitListAdapter;

    private FriendFragment() {
        // Required empty public constructor
    }

    public static FriendFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (FriendFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new FriendFragment();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<ShitListItem>();
//        List<CommentListItem> commentList = new ArrayList<CommentListItem>();
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//        commentList.add(new CommentListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", new ArrayList<ReplyListItem>(), "18:00"));
//
//        list.add(new ShitListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", "17:50", 50, null, commentList));
//        list.add(new ShitListItem(new UserData("Ann@163.com", "Ann", "1234"), "测试内容", "17:50", 50, null, commentList));
//        list.add(new ShitListItem(new UserData("Paul@163.com", "Paul", "1234"), "测试内容", "17:50", 50, null, commentList));
//        list.add(new ShitListItem(new UserData("Peter@163.com", "Peter", "1234"), "测试内容", "17:50", 50, null, commentList));
//        list.add(new ShitListItem(new UserData("Brown@163.com", "Brown", "1234"), "测试内容", "17:50", 50, null, commentList));
        shitListAdapter = new ShitListAdapter(getActivity(),list);
        setListAdapter(shitListAdapter);
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                pairList.add(new BasicNameValuePair("email", savedSearches.getString("email", null)));
                pairList.add(new BasicNameValuePair("password",savedSearches.getString("password", null)));

                JSONArray json = HttpUtil.PostArray("http://tucao.vxpai.com/initventlist", pairList);

                for(int i = 0;i < json.length();i++){
                    try {
                        JSONObject obj = new JSONObject(json.get(i).toString());
                        if(obj.getString("isannoy").equals("NO")) {
                            list.add(new ShitListItem(new UserData(obj.getString("email"), obj.getString("username"), null), obj.getString("content"), new Timestamp(obj.getLong("posttime")).toString(), obj.getInt("approveNum"), obj.getInt("cid"), null, new ArrayList<CommentListItem>()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tt.start();
        try {
            tt.join();
            shitListAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.friend_fragment,container,false);
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            mListener.onShowDetailShits(list.get(position));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setSavedSearches(SharedPreferences savedSearches){ this.savedSearches = savedSearches;}

    public void refresh(){
        list.clear();
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                pairList.add(new BasicNameValuePair("email", savedSearches.getString("email", null)));
                pairList.add(new BasicNameValuePair("password",savedSearches.getString("password", null)));

                JSONArray json = HttpUtil.PostArray("http://tucao.vxpai.com/initventlist", pairList);

                for(int i = 0;i < json.length();i++){
                    try {
                        JSONObject obj = new JSONObject(json.get(i).toString());
                        if(obj.getString("isannoy").equals("NO")) {
                            list.add(new ShitListItem(new UserData(obj.getString("email"), obj.getString("username"), null), obj.getString("content"), new Timestamp(obj.getLong("posttime")).toString(), obj.getInt("approveNum"), obj.getInt("cid"), null, new ArrayList<CommentListItem>()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tt.start();
        try {
            tt.join();
            shitListAdapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
