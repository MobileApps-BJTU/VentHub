package com.vxpai.venthub;


import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vxpai.Adapter.CommentListAdapter;
import com.vxpai.entity.ShitListItem;
import com.vxpai.interfaces.OnFragmentInteractionListener;
import com.vxpai.utils.ImageUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailShitsFragment extends Fragment {

    private static DetailShitsFragment mInstance;
    private View rootView;
    private ImageView backToShits, headImg, priaseBtn, commentBtn;
    private TextView nickName, shitTime, shitDetail, praiseNum;
    private GridView imageContainer;
    private ListView commentList;

    private CommentListAdapter commentListAdapter;

    private ShitListItem detailShit;

    private OnFragmentInteractionListener mListener;

    private DetailShitsFragment() {
        // Required empty public constructor
    }

    public static DetailShitsFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (DetailShitsFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new DetailShitsFragment();
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
        if (rootView == null)
        {
            rootView = inflater.inflate(R.layout.detail_shits_container_frament, container, false);

            headImg = (ImageView)rootView.findViewById(R.id.id_user_image_detail_shits);
            nickName = (TextView)rootView.findViewById(R.id.id_username_detail_shits);
            shitTime = (TextView)rootView.findViewById(R.id.id_datetime_detail_shits);
            praiseNum = (TextView)rootView.findViewById(R.id.id_praise_num_detail_shits);
            shitDetail = (TextView)rootView.findViewById(R.id.id_shits_content);

            priaseBtn = (ImageView)rootView.findViewById(R.id.id_praise_the_shit);
            commentBtn = (ImageView)rootView.findViewById(R.id.id_comment_the_shit);

            backToShits = (ImageView)rootView.findViewById(R.id.id_back_to_shits);
            backToShits.setOnClickListener(backToShitsListener);

            imageContainer = (GridView)rootView.findViewById(R.id.id_image_container);
            commentList = (ListView)rootView.findViewById(R.id.id_comment_list);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null)
        {
            parent.removeView(rootView);
        }

        nickName.setText(detailShit.getUserData().getUsername());
        shitTime.setText(detailShit.getTime());
        praiseNum.setText("" + detailShit.getPraiseNum());
        shitDetail.setText(detailShit.getContent());

        commentListAdapter = new CommentListAdapter(getActivity(), detailShit.getCommentList());
        commentList.setAdapter(commentListAdapter);

        return rootView;
    }

    private View.OnClickListener priaseBtnListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener commentBtnListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            // commentListAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener backToShitsListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            mListener.onGoBackToMain();
        }
    };

    public void setDetailShit(ShitListItem detailShit) {
        this.detailShit = detailShit;
    }
}
