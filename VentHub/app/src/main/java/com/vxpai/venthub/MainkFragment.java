package com.vxpai.venthub;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vxpai.utils.ImageUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainkFragment extends Fragment {

    private static MainkFragment mInstance;
    private ImageView mUserMenu;

    private MainkFragment() {
        // Required empty public constructor
    }

    public static MainkFragment getInstance()
    {

        if (mInstance == null)
        {
            synchronized (MainkFragment.class)
            {
                if (mInstance == null)
                {
                    mInstance = new MainkFragment();
                }
            }
        }
        return mInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        mUserMenu = (ImageView)view.findViewById(R.id.id_user_menu);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.battlefield);
        mUserMenu.setImageBitmap(ImageUtil.createCircleImage(bmp, bmp.getWidth()));
        if(!bmp.isRecycled())
            bmp.recycle();
        return view;
    }
}
