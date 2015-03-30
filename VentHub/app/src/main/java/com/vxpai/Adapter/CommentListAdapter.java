package com.vxpai.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vxpai.entity.CommentListItem;
import com.vxpai.entity.FriendListItem;
import com.vxpai.venthub.R;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/30.
 */
public class CommentListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CommentListItem> commentList;
    private Context context;

    private LruCache<String, Bitmap> mLruCache;
    private ReplyListAdapter replyListAdapter;

    public CommentListAdapter(Context context, List<CommentListItem> commentList){
        this.inflater = LayoutInflater.from(context);
        this.commentList = commentList;
        this.context = context;

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
        return commentList.size();
    }

    @Override
    public CommentListItem getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_detail_comment, null);
        ImageView headImg = (ImageView)view.findViewById(R.id.id_head_comment);
        TextView nickName = (TextView)view.findViewById(R.id.id_nickname_comment);
        TextView commentTime = (TextView)view.findViewById(R.id.id_datetime_comment);
        LinearLayout replyComment = (LinearLayout)view.findViewById(R.id.id_reply_comment);
        TextView commentDetail = (TextView)view.findViewById(R.id.id_detail_comment);
        ListView replyList = (ListView)view.findViewById(R.id.id_replylist_comment);

        // headImg
        nickName.setText(commentList.get(position).getNickName());
        commentTime.setText(commentList.get(position).getCommentTime());
        commentDetail.setText(commentList.get(position).getCommentDetail());

        replyListAdapter = new ReplyListAdapter(context, commentList.get(position).getReplyList());
        replyList.setAdapter(replyListAdapter);
        return view;
    }

    private View.OnClickListener replyCommentListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            // replyListAdapter.notifyDataSetChanged();
        }
    };
}
