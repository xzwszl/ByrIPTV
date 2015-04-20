package com.example.byriptv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayActivity extends Activity implements OnClickListener {
	
	private int mPosition = -1;
	private VideoView mVideoView;
	private ListView mListView;
	private RelativeLayout mTools;
	private View mRoot;
	private TextView mBack;
	private TextView mSmallWindow;
	private TextView mList;
	private boolean isShownTools = true;
	private boolean isShowList = true;
	
	private boolean isenforceHide = false;
	private ProgressBar mProgressbar;
	
	

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_play);
		
		init();
	}
	
	
	@Override
	public void onBackPressed() {
		
		mVideoView.stopPlayback();
		super.onBackPressed();
	}
	
	private void init() {
		
		
		mPosition = getIntent().getIntExtra("position", -1);
		
		mVideoView = new VideoView(getApplicationContext());//(VideoView) findViewById(R.id.videoview_play);
		mVideoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				mProgressbar.setVisibility(View.GONE);
				//�������
			}
		});
				
		mListView = (ListView) findViewById(R.id.listview_tvs);
		mListView.setAdapter(new MyAdapter(getApplicationContext(), getResources().getColor(android.R.color.white),14));
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (Utils.isWifiOpen(getApplicationContext())) {
					mPosition = position;
					playMovie();
				} else {
					Utils.displayMessage(getApplicationContext(), "������WiFi~~");
				}
			}	
		});	
		
		mProgressbar = (ProgressBar) findViewById(R.id.progressbar);
		mRoot = (View) findViewById(R.id.root);
		mBack = (TextView) findViewById(R.id.textview_back);
		mSmallWindow = (TextView) findViewById(R.id.textview_smallwindow);
		mList = (TextView) findViewById(R.id.textview_list);
		mTools = (RelativeLayout) findViewById(R.id.relativelayout_tools);
		mRoot.setOnClickListener(this);
		mBack.setOnClickListener(this);
		mSmallWindow.setOnClickListener(this);
		mList.setOnClickListener(this);
		
		
		((FrameLayout)mRoot.getParent()).addView(mVideoView,0);
		playMovie();
	}
	
	private void playMovie() {
		
		mProgressbar.setVisibility(View.VISIBLE);
		mVideoView.stopPlayback();
		Uri uri = Uri.parse(AppConfig.iPtvUrls[2 * mPosition]);
		
		mVideoView.setVideoURI(uri);
		
		mVideoView.start();
		
	}
	
	private void showList() {
		
		if (!isShowList) {
			
			TranslateAnimation animation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 1f, 
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF, 0f);
			animation.setDuration(250);
			mListView.setAnimation(animation);
			mListView.setVisibility(View.VISIBLE);
			isShowList = true;
		}
	}
	
	private void hideList() {
		
		if (isShowList) {
			
			TranslateAnimation animation = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF, 1f, 
					Animation.RELATIVE_TO_SELF, 0f, 
					Animation.RELATIVE_TO_SELF, 0f);
//			TranslateAnimation animation = new TranslateAnimation(
//					0,
//					mListView.getWidth(),
//					0,
//					0);
			animation.setDuration(250);
			mListView.setAnimation(animation);
			mListView.setVisibility(View.GONE);
			isShowList = false;
		}
	}
	
	private void showTools() {
		
		if (!isShownTools) {
			
//			TranslateAnimation animation = new TranslateAnimation(
//					Animation.RELATIVE_TO_SELF, 0f, 
//					Animation.RELATIVE_TO_SELF, 0f, 
//					Animation.RELATIVE_TO_SELF, 0f, 
//					Animation.RELATIVE_TO_SELF, 1f);
//			animation.setDuration(250);
//			animation.setAnimationListener(new AnimationListener() {
//				
//				@Override
//				public void onAnimationStart(Animation animation) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onAnimationRepeat(Animation animation) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					// TODO Auto-generated method stub
//					if (!isenforceHide)
//						showList();
//				}
//			});
//			
//			mTools.setAnimation(animation);
			mTools.setVisibility(View.VISIBLE);
			isShownTools = true;
			
			if (!isenforceHide)
				showList();
		}
	}
	
	private void hideTools() {
		
		if (isShownTools) {
			
			mTools.setVisibility(View.GONE);
			isShownTools = false;
		}
		
		hideList();
		
	}
	
	private void showSmallWindow() {
		
		final FrameLayout layout = (FrameLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_main, null);
//		
//		
		final VideoView video = (VideoView) layout.findViewById(R.id.videoview_play);
//		
		Uri uri = Uri.parse(AppConfig.iPtvUrls[2 * mPosition]);
//		
		video.setVideoURI(uri);
//		

		
//		((FrameLayout)mVideoView.getParent()).removeView(mVideoView);
		
//		mVideoView.resume();
		
		layout.setBackgroundColor(Color.TRANSPARENT);
		
		final WindowManager wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
	
		params.gravity = Gravity.CENTER;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		
		params.format = PixelFormat.TRANSLUCENT;
		
		params.flags = 40;
		//WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		
		params.width = (int) (300 * getResources().getDisplayMetrics().density);
		
		params.height = (int) ( params.width * getResources().getDisplayMetrics().heightPixels / getResources().getDisplayMetrics().widthPixels);
		
		params.x = 0;
		params.y = 0;
	
		layout.setOnTouchListener(new OnTouchListener(){

			float lastX,lastY,initX,initY;
			float mTouchSlop = ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();
			
			RectF rectF = new RectF();
			
			float mRawX,mRawY;
			float curDis = 0;
			int minHeight = params.width / 2;
			int minWidth = params.height / 2;
			long start = -1;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				rectF.left = layout.getLeft();
				rectF.right = layout.getRight();
				rectF.top = layout.getTop();
				rectF.bottom = layout.getBottom();
				mRawX = event.getRawX();
				mRawY = event.getRawY();
								
//				wm.removeView(layout);
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				
				case MotionEvent.ACTION_DOWN:
					initX = lastX = event.getRawX() - params.x;
					initY = lastY = event.getRawY() - params.y;
					
					if (start == -1) {
						start = System.currentTimeMillis();
					} else {
						//
						long time = System.currentTimeMillis();
						
						if (time - start <= 500) {
							video.stopPlayback();
							wm.removeView(layout);
						}
						start = -1;
					}
					break;
				case MotionEvent.ACTION_MOVE:
					//防止抖动，同时如果滑出界忽略
					
					if (event.getPointerCount() >=2) {
						float dis = getDis(event);
						if (Math.abs(dis - curDis) >= 1) {
							int width = (int) (params.width + dis - curDis);
							int height =  (int)(params.height + dis - curDis);
							
							if (width >= minWidth && height >= minHeight) {
								params.x += (dis - curDis) /2;
								params.y += (dis - curDis)/2;
								params.width = width;
								params.height = height;
								curDis = dis;
							}
						}
					} else {
						float x = event.getX(),y = event.getY();
						if (rectF.contains(x, y)) {
							int absX = (int) Math.abs(mRawX - lastX);
							int absY = (int) Math.abs(mRawY - lastY);
							
							if (absX >= mTouchSlop || absY >= mTouchSlop) {
								params.x = (int) (mRawX - initX);
								params.y = (int) (mRawY - initY);
//								Log.e("-------------------x = ",x + "  ");
//								Log.e("-------------------initX = ",initX + "  ");
//								Log.e("-------------------params.x = ",params.x + "  ");
//								Log.e("-------------------y = ",y + "  ");
//								Log.e("-------------------initY = ",initY + "  ");
//								Log.e("-------------------params.y = ",params.y + "  ");
							} else return false;
						}
					}
					wm.updateViewLayout(layout, params);
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if (event.getPointerCount() ==2) {
						curDis = getDis(event);
					}
					break;
				case MotionEvent.ACTION_POINTER_UP:
					break;
					
				default:
					break;
				}
				return true;
			}			
		});
		
		wm.addView(layout, params);
		
//		finish();
		
		onBackPressed();
		video.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				
				layout.findViewById(R.id.progressbar).setVisibility(View.GONE);
				layout.findViewById(R.id.view).setVisibility(View.GONE);
			}
		});
		video.start();
	}
	
	private float getDis(MotionEvent event) {
		float dx = event.getX(0) - event.getX(1);
		float dy = event.getY(0) - event.getY(1);
		
		return (float)Math.sqrt(dx * dx + dy * dy);
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textview_back:
			onBackPressed();
			break;
		case R.id.textview_smallwindow:
			showSmallWindow();
//			new TableShowView(getApplicationContext()).fun();
			//Utils.displayMessage(getApplicationContext(), "��ʱ��֧�ָù���");
			break;
		case R.id.textview_list:
			
			if (!isShowList) {
				showList();
				isenforceHide = false;
			} else {
				hideList();
				isenforceHide = true;
			}
			break;
		case R.id.root:
			if (!isShownTools) {
				showTools();
			} else {
				hideTools();
			}
			break;
		default:
			break;
		}
	}
	
	
}
