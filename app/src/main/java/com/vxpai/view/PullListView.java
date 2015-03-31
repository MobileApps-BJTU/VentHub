package com.vxpai.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vxpai.venthub.R;

public class PullListView extends ListView {

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;

	private LayoutInflater inflater;
	private LinearLayout headView;

	private TextView tipsTextview;
	private ImageView arrowImageView;
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;

	private int headContentHeight;

	private int startY;
	private int tempY;
	private int state;
	private boolean isBack;
	private OnRefreshListener refreshListener;
	
	private int dist;

	private boolean isRefreshable;

	public PullListView(Context context) {
		super(context);
		init(context);
	}

	public PullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		headView = (LinearLayout) inflater.inflate(R.layout.pull_header, null);
		arrowImageView = (ImageView) headView
				.findViewById(R.id.head_arrowImage);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_text);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		
		Log.v("procedure","start and the height of the head is "+headContentHeight);
		
		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		addHeaderView(headView, null, false);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
		isRecored = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!isRecored) {
					isRecored = true;
					startY = (int) event.getY();
					
					Log.v("action", "start and startY is " + startY);
				}
				break;
			case MotionEvent.ACTION_UP:
				Log.v("move","end and tempY - startY is"+(tempY - startY));
				
				if (state != REFRESHING) {
					if (state == DONE) {
						// ʲô������
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();
					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				tempY = (int) event.getY();

				if (!isRecored) {
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored) {
					// ��֤������padding�Ĺ����У���ǰ��λ��һֱ����head������������б�����Ļ�Ļ����������Ƶ�ʱ���б��ͬʱ���й���
					// ��������ȥˢ����
					if (state == RELEASE_To_REFRESH) {
						setSelection(0);
						// �������ˣ��Ƶ�����Ļ�㹻�ڸ�head�ĳ̶ȣ����ǻ�û���Ƶ�ȫ���ڸǵĵز�
						if (((tempY - startY) < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
						// һ�����Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
						// �������ˣ����߻�û�����Ƶ���Ļ�����ڸ�head�ĵز�
						else {
							// ���ý����ر�Ĳ�����ֻ�ø���paddingTop��ֵ������
						}
					}
					// ��û�е�����ʾ�ɿ�ˢ�µ�ʱ��,DONE������PULL_To_REFRESH״̬
					if (state == PULL_To_REFRESH) {
						setSelection(0);
						// ���������Խ���RELEASE_TO_REFRESH��״̬
						if ((tempY - startY) >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();
						}
						// ���Ƶ�����
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();
						}
					}
					// done״̬��
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}
					// ����headView��size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY), 0, 0);
					}
					// ����headView��paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY)
								- headContentHeight, 0, 0);
					}
					
					Log.v("action", "move and tempY is " + tempY);
				}
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	// ��״̬�ı�ʱ�򣬵��ø÷������Ը��½���
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText(getResources().getString(
					R.string.release_to_refresh));

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setText(getResources().getString(
					R.string.pull_to_refresh));
			tipsTextview.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// ����RELEASE_To_REFRESH״̬ת������
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText(getResources().getString(
						R.string.pull_to_refresh));
			} else {
				tipsTextview.setText(getResources().getString(
						R.string.pull_to_refresh));
			}

			break;

		case REFRESHING:		
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (dist = -1 * headContentHeight
							+ (tempY - startY); dist > 0; dist-=5) {
						try {
							Thread.sleep(1);

							myHandler.sendEmptyMessage(0);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText(getResources().getString(R.string.refreshing));

			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.refresh);
			tipsTextview.setText(getResources().getString(
					R.string.complete));
			
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//					
//					for (dist = 0; dist > -1 * headContentHeight; dist --) {
//						try {
//							Thread.sleep(1);
//
//							myHandler.sendEmptyMessage(0);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}).start();
			
			break;
		}
	}

	public void setonRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete() {
		state = DONE;
		changeHeaderViewByState();
		invalidateViews();
		setSelection(0);
	}

	private void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void setAdapter(BaseAdapter adapter) {
		super.setAdapter(adapter);
	}
	
	private Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			headView.setPadding(0, dist, 0, 0);
		}
    };

}
