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

import com.vxpai.entity.ShitListItem;
import com.vxpai.utils.ImageUtil;
import com.vxpai.venthub.R;

import java.util.List;

/**
 * Created by 俊成 on 2015/3/28.
 */
public class ShitListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ShitListItem> shitListItemList;
    private Context mContext;

    private LruCache<String, Bitmap> mLruCache;

    public ShitListAdapter(Context context, List<ShitListItem> shitListItemList){
        this.inflater = LayoutInflater.from(context);
        this.shitListItemList = shitListItemList;
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
        return shitListItemList.size();
    }

    @Override
    public ShitListItem getItem(int position) {
        return shitListItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_shits_content, null);

        ImageView headImg = (ImageView)view.findViewById(R.id.id_user_image_shits);

        ImageView contentImg1 = (ImageView)view.findViewById(R.id.id_image_shits1);
        ImageView contentImg2 = (ImageView)view.findViewById(R.id.id_image_shits2);
        ImageView contentImg3 = (ImageView)view.findViewById(R.id.id_image_shits3);

        TextView userName = (TextView)view.findViewById(R.id.id_username_shits);
        TextView dataTime = (TextView)view.findViewById(R.id.id_datetime_shits);
        TextView praise = (TextView)view.findViewById(R.id.id_praise_shits);
        TextView content = (TextView)view.findViewById(R.id.id_shits_content);

        Bitmap bitmap;

        if((bitmap = mLruCache.get("mike")) != null){
            headImg.setImageBitmap(bitmap);
        }else{
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battlefield);
            mLruCache.put("mike", ImageUtil.createCircleImage(bmp, bmp.getWidth()));
            headImg.setImageBitmap(mLruCache.get("mike"));
            if(!bmp.isRecycled())
                bmp.recycle();
        }

        userName.setText(shitListItemList.get(position).getUserData().getUsername());
        dataTime.setText(shitListItemList.get(position).getTime());
        praise.setText("" + shitListItemList.get(position).getPraiseNum());
        content.setText(shitListItemList.get(position).getContent());

        return view;
    }
}
