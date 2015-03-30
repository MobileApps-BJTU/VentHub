package com.vxpai.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vxpai.entity.CommentListItem;
import com.vxpai.entity.ReplyListItem;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class ReplyListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ReplyListItem> replyList;
    private Context context;

    private LruCache<String, Bitmap> mLruCache;

    public ReplyListAdapter(Context context, List<ReplyListItem> replyList) {
        this.context = context;
        this.replyList = replyList;
        this.inflater = LayoutInflater.from(context);

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
        return replyList.size();
    }

    @Override
    public ReplyListItem getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
