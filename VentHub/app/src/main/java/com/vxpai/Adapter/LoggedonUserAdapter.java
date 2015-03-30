package com.vxpai.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vxpai.entity.User;
import com.vxpai.utils.ImageUtil;
import com.vxpai.venthub.R;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class LoggedonUserAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<User> userList;
    private Context mContext;

    private LruCache<String, Bitmap> mLruCache;

    public LoggedonUserAdapter(Context context, List<User> userList){
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.userList = userList;

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
        return userList.size();
    }

    @Override
    public User getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_loggedon_user, null);

        ImageView userImage = (ImageView)view.findViewById(R.id.id_loggedon_image);
        TextView username = (TextView)view.findViewById(R.id.id_loggedon_username);
        ImageView deleteUser = (ImageView)view.findViewById(R.id.id_delete_user);

        //int width = ImageUtil.getImageViewWidth(userImage);
        //Bitmap bmp = ImageUtil.createSuitableImg(userList.get(position).getImagePath(), width, width);
        //userImage.setImageBitmap(ImageUtil.createSuitableImg(userList.get(position).getImagePath(), 150, 150));

        Bitmap bitmap;

        if((bitmap = mLruCache.get("mike")) != null){
            userImage.setImageBitmap(bitmap);
        }else{
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battlefield);
            mLruCache.put("mike", ImageUtil.createCircleImage(bmp, bmp.getWidth()));
            userImage.setImageBitmap(mLruCache.get("mike"));
            if(!bmp.isRecycled())
                bmp.recycle();
        }

        username.setText(userList.get(position).getUsername());
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
