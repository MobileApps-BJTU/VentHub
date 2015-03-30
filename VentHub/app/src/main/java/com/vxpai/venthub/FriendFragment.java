package com.vxpai.venthub;

import android.app.Activity;
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
        list.add(new ShitListItem(new UserData("Mike@163.com", "Mike", "1234"), "测试内容", "17:50", 50, null, new ArrayList<CommentListItem>()));
        list.add(new ShitListItem(new UserData("Ann@163.com", "Ann", "1234"), "测试内容", "17:50", 50, null, new ArrayList<CommentListItem>()));
        list.add(new ShitListItem(new UserData("Paul@163.com", "Paul", "1234"), "测试内容", "17:50", 50, null, new ArrayList<CommentListItem>()));
        list.add(new ShitListItem(new UserData("Peter@163.com", "Peter", "1234"), "测试内容", "17:50", 50, null, new ArrayList<CommentListItem>()));
        list.add(new ShitListItem(new UserData("Brown@163.com", "Brown", "1234"), "测试内容", "17:50", 50, null, new ArrayList<CommentListItem>()));

        setListAdapter(new ShitListAdapter(getActivity(),list));
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
