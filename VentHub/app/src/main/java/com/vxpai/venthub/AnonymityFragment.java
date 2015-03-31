package com.vxpai.venthub;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.vxpai.Adapter.ShitListAdapter;
import com.vxpai.entity.CommentListItem;
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
public class AnonymityFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;

    private static AnonymityFragment mInstance;

    private List<ShitListItem> list = new ArrayList<ShitListItem>();

    private SharedPreferences savedSearches;

    private ShitListAdapter shitListAdapter;

    private AnonymityFragment() {
        // Required empty public constructor
    }

    public static AnonymityFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (AnonymityFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new AnonymityFragment();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<ShitListItem>();
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
                        if(!obj.getString("isannoy").equals("NO")) {
                            list.add(new ShitListItem(new UserData(obj.getString("email"), getResources().getString(R.string.annoy_user), null), obj.getString("content"), new Timestamp(obj.getLong("posttime")).toString(), obj.getInt("approveNum"), obj.getInt("cid"), null, new ArrayList<CommentListItem>()));
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
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onShowDetailShits(list.get(position));
        }
    }

    public void setSavedSearches(SharedPreferences savedSearches){ this.savedSearches = savedSearches;}

//    public void refresh(){
//        list.clear();
//        Thread tt = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<NameValuePair> pairList = new ArrayList<NameValuePair>();
//                pairList.add(new BasicNameValuePair("email", savedSearches.getString("email", null)));
//                pairList.add(new BasicNameValuePair("password",savedSearches.getString("password", null)));
//
//                JSONArray json = HttpUtil.PostArray("http://tucao.vxpai.com/initventlist", pairList);
//
//                for(int i = 0;i < json.length();i++){
//                    try {
//                        JSONObject obj = new JSONObject(json.get(i).toString());
//                        if(obj.getString("isannoy").equals("NO")) {
//                            list.add(new ShitListItem(new UserData(obj.getString("email"), obj.getString("username"), null), obj.getString("content"), new Timestamp(obj.getLong("posttime")).toString(), obj.getInt("approveNum"), obj.getInt("cid"), null, new ArrayList<CommentListItem>()));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        tt.start();
//        try {
//            tt.join();
//            shitListAdapter.notifyDataSetChanged();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

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
