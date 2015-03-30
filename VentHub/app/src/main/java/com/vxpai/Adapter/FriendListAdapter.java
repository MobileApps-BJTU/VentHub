package com.vxpai.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vxpai.entity.FriendListItem;
import com.vxpai.venthub.R;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/29.
 */
public class FriendListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<FriendListItem> friendList;
    private Context mContext;

    private LruCache<String, Bitmap> mLruCache;

    public FriendListAdapter(Context context, List<FriendListItem> friendList){
        this.inflater = LayoutInflater.from(context);
        this.friendList = friendList;
        this.mContext = context;

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            };
        };
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_friendlist, null);

        ImageView image = (ImageView)view.findViewById(R.id.id_friend_list_img);
        TextView username = (TextView)view.findViewById(R.id.id_friendlist_username);
        ImageView delete = (ImageView)view.findViewById(R.id.id_friendlist_delete);

        username.setText(friendList.get(position).getmNickName());

        return view;
    }
}
