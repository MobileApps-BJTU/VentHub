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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        userName.setText("丿灬樱花笑");
        dataTime.setText("16:50");
        praise.setText("500");
        content.setText("今天要做一个搜索功能，搜索界面采用AutoCompleteTextView做搜索条，然后下面用listview来显示搜索结果，而我的主界面是在底部用tab做了一个主界面导航，其中有一个搜索按钮，因为在搜索条中输入文字的时候会弹出软件盘，但是如果不做什么设置的话，软键盘弹出来的同时，会把我下面的tab导航给相应拉到屏幕的上面，界面显示的扭曲啊，后来找到一种解决方法，在相应的activity中（比如我这是tab的activity，用的是adjustpan）添加");

        return view;
    }
}
