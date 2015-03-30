package com.vxpai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.vxpai.utils.ScreenUtils;
import com.vxpai.venthub.R;

public class SlidingMenu extends HorizontalScrollView
{
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;
	/**
	 * dp
	 */
	private int mMenuRightPadding;
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOpen = false;

	private boolean once;

    //private float lastX, lastY;

    //private GestureDetector mGestureDetector;

    //private SlidingMenu mSlidingMenu;

	public SlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);
        //mGestureDetector = new GestureDetector(context, new MyOnGestureListener());
        //mSlidingMenu = this;

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.SlidingMenu_rightPadding:
				// 默认50
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();
	}

	public SlidingMenu(Context context)
	{
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * 显示的设置一个宽度
		 */
		if (!once)
		{
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);

			mMenuWidth = mScreenWidth / 4 * 3;
			mHalfMenuWidth = 10;
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{

		int action = ev.getAction();
        //mGestureDetector.onTouchEvent(ev);
		switch (action)
		{
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > 10)
			{
                if(isOpen){
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                }else{
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }

			}
            return true;
		}
		return super.onTouchEvent(ev);
	}

//    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            super.onFling(e1, e2, velocityX, velocityY);
//
//            if(e1.getX() <  e2.getX()){
//                if(!isOpen){
//                    mSlidingMenu.smoothScrollTo(0, 0);
//                    isOpen = true;
//                }
//            }else{
//                if(isOpen){
//                    mSlidingMenu.smoothScrollTo(mMenuWidth, 0);
//                    isOpen = false;
//                }
//            }
//            return true;
//        }
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return super.onDown(e);
//        }
//    }
}
