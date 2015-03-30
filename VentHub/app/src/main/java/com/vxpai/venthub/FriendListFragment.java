package com.vxpai.venthub;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.vxpai.Adapter.FriendListAdapter;
import com.vxpai.entity.FriendListItem;
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
public class FriendListFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;
    private View rootView;

    private static FriendListFragment mInstance;

    private boolean whetherExit = false;

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

        List<FriendListItem> list = new ArrayList<FriendListItem>();
        list.add(new FriendListItem("","Mike"));
        list.add(new FriendListItem("","Ann"));
        list.add(new FriendListItem("","Peter"));
        list.add(new FriendListItem("","Paul"));
        list.add(new FriendListItem("","John"));

        setListAdapter(new FriendListAdapter(getActivity(),list));
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
        }
    }

    public void setWhetherExit(boolean whetherExit){ this.whetherExit = whetherExit; }

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
