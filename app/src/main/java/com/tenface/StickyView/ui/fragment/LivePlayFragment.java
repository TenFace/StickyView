package com.tenface.StickyView.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tenface.StickyView.R;
import com.tenface.StickyView.ui.activity.LiveActivity;

/**
 * Created by ccy on 2016/9/18.
 */
public class LivePlayFragment extends Fragment implements ITXLivePlayListener {

	private boolean isLive;
	private TXLivePlayConfig mPlayConfig;
	private TXCloudVideoView mPlayerView;
	private ImageView mLoadingView;
	private Button mBtnPlay;
	private Button mBtnPlay1;
	private boolean mVideoPause;
	private TXLivePlayer mLivePlayer;
	private boolean mHWDecode;
	private View progressGroup;
	private Button mBtnRenderMode;
	private Button mBtnHWDecode;
	private Button mBtnCacheStrategy;
	private LinearLayout mLayoutCacheStrategy;
	private Button mBtnOrientation;
	private RadioGroup mCacheStrategyRadioGroup;
	private Context mContext;
	private String liveUrl = null;
	private String playUrl = null;

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_live_play, container, false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (isLive) {
			view.setBackgroundResource(R.drawable.main_tab_bkg);
		} else {
			view.setBackgroundResource(R.drawable.main_tab_bkg);
		}
		init(view);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mLayoutCacheStrategy.setVisibility(View.GONE);
			}
		});
	}

	private void init(View view) {
		initView(view);
		initData();
		initListener();
	}

	private boolean mVideoPlay;

	private void initListener() {
		mVideoPlay = false;
		//检查当前是否是播放一个直播数据源
		if (isLive) {
			progressGroup.setVisibility(View.GONE);
		}
		mBtnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mVideoPlay) {
					if (!isLive) {
						if (mVideoPause) {
							mLivePlayer.resume();
							mBtnPlay.setBackgroundResource(R.drawable.play_pause);
						} else {
							mLivePlayer.pause();
							mBtnPlay.setBackgroundResource(R.drawable.play_start);
						}
						mVideoPause = !mVideoPause;
					} else {
						stopPlayRtmp();
						mVideoPlay = !mVideoPlay;
					}
				} else {
					if (startPlayRtmp()) {
						mVideoPlay = !mVideoPlay;
					}
				}
			}
		});
		mBtnOrientation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLivePlayer == null) {
					return;
				}

				//判断如果方向是横屏,则切换为竖屏,否则切换为横屏
				if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_PORTRAIT) {
					mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_LANDSCAPE;
					mBtnOrientation.setBackgroundResource(R.drawable.portrait);
				} else if (mCurrentRenderRotation == TXLiveConstants.RENDER_ROTATION_LANDSCAPE) {
					mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
					mBtnOrientation.setBackgroundResource(R.drawable.landscape);
				}

				mLivePlayer.setRenderRotation(mCurrentRenderRotation);

			}
		});

		mBtnRenderMode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mLivePlayer == null) {
					return;
				}

				if (mCurrentRenderMode == TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION) {
					mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
					mBtnRenderMode.setBackgroundResource(R.drawable.adjust_mode);
					mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
				} else if (mCurrentRenderMode == TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN) {
					mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
					mBtnRenderMode.setBackgroundResource(R.drawable.fill_mode);
					mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
				}
			}
		});

		mBtnHWDecode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHWDecode = !mHWDecode;
				mBtnHWDecode.getBackground().setAlpha(mHWDecode ? 255 : 100);
				if (mHWDecode) {
					Toast.makeText(getActivity(), "已经开启硬件加速,切换会重启播放流程", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "已经关闭硬件加速,切换会重启播放流程", Toast.LENGTH_SHORT).show();
				}

				if (mVideoPlay) {
					stopPlayRtmp();
					startPlayRtmp();
				}
			}

		});

		mBtnCacheStrategy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mLayoutCacheStrategy.setVisibility(View.VISIBLE);
			}
		});

		setCacheStrategy(CACHE_STRATEGY_AUTO);

		mCacheStrategyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId){
					case R.id.radio_btn_fast:
						setCacheStrategy(CACHE_STRATEGY_FAST);
						break;
					case R.id.radio_btn_smooth:
						setCacheStrategy(CACHE_STRATEGY_SMOOTH);
						break;
					case R.id.radio_btn_auto:
						setCacheStrategy(CACHE_STRATEGY_AUTO);
						break;
				}
			}


		});

	}
	private int mCurrentCacheStrategy=CACHE_STRATEGY_AUTO;
	private void setCacheStrategy(int cacheStrategy) {
		if(mCurrentCacheStrategy==cacheStrategy){
			return;
		}
		switch (cacheStrategy){
			case CACHE_STRATEGY_FAST:
				mPlayConfig.setAutoAdjustCacheTime(true);
				mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_FAST);
				mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST);
				mLivePlayer.setConfig(mPlayConfig);
				break;
			case CACHE_STRATEGY_SMOOTH:
				mPlayConfig.setAutoAdjustCacheTime(false);
				mPlayConfig.setCacheTime(CACHE_TIME_SMOOTH);
				mLivePlayer.setConfig(mPlayConfig);
				break;
			case CACHE_STRATEGY_AUTO:
				mPlayConfig.setAutoAdjustCacheTime(true);
				mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_AUTO_MAX);
				mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_AUTO_MIN);
				mLivePlayer.setConfig(mPlayConfig);
				break;
		}
	}

	private static final int CACHE_TIME_FAST=1;
	private static final int CACHE_TIME_SMOOTH=5;

	private static final int CACHE_TIME_AUTO_MAX=10;
	private static final int CACHE_TIME_AUTO_MIN=5;

	private static final int CACHE_STRATEGY_FAST=1;
	private static final int CACHE_STRATEGY_SMOOTH=2;
	private static final int CACHE_STRATEGY_AUTO=3;



	private boolean startPlayRtmp() {

		if (liveUrl != null){
			playUrl = liveUrl;//香港卫视
			;
		}
		mBtnPlay.setBackgroundResource(R.drawable.play_pause);
		//让播放器显示的数据显示到播放VideoView上
		mLivePlayer.setPlayerView(mPlayerView);
		//设置播放监听
		mLivePlayer.setPlayListener(this);
		//设置屏幕方向为竖直方向
		mLivePlayer.setRenderRotation(mCurrentRenderRotation);
		//设置屏幕缩放模式为自适应
		mLivePlayer.setRenderMode(mCurrentRenderMode);
		mLivePlayer.setConfig(mPlayConfig);
		//参数1:播放地址
		//参数2:播放类型:直播类型和非直播类型
		int result = mLivePlayer.startPlay(playUrl, isLive ? 0 : 1);
		if (result == -2) {
			Toast.makeText(getActivity(), "非腾讯云的地址,若开放限制,才可以播放", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}

	private void stopPlayRtmp() {
		mBtnPlay.setBackgroundResource(R.drawable.play_start);
		if (mLivePlayer != null) {
			mLivePlayer.setPlayListener(null);
			mLivePlayer.stopPlay(true);
		}
	}

	//视频是自适应屏幕的,分辨率越高,视频宽高越大
	private int mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
	//视频方向默认竖直方向
	private int mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;

	private void initData() {
		mContext = this.getContext();
		mPlayConfig = new TXLivePlayConfig();
		if (mLivePlayer == null) {
			mLivePlayer = new TXLivePlayer(getActivity());
		}

		Bundle bundle1 = getArguments();
		if (bundle1!=null) {
			liveUrl = bundle1.getString("infoUrl");
		}
	}

	private void initView(View view) {
		mPlayerView = (TXCloudVideoView) view.findViewById(R.id.video_view);
		mLoadingView = (ImageView) view.findViewById(R.id.loadingImageView);
		mBtnPlay = (Button) view.findViewById(R.id.btnPlay);
		progressGroup = view.findViewById(R.id.play_progress);
		mBtnOrientation = (Button) view.findViewById(R.id.btnOrientation);
		mBtnRenderMode = (Button) view.findViewById(R.id.btnRenderMode);
		mBtnHWDecode=(Button) view.findViewById(R.id.btnHWDecode);

		mBtnCacheStrategy=(Button)view.findViewById(R.id.btnCacheStrategy);
		mLayoutCacheStrategy=(LinearLayout)view.findViewById(R.id.layoutCacheStrategy);
		mCacheStrategyRadioGroup= (RadioGroup) view.findViewById(R.id.cacheStrategyRadioGroup);

	}


	@Override
	public void onPlayEvent(int event, Bundle param) {
		if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
			stopPlayRtmp();
			mVideoPlay = false;
			mVideoPause = false;
			//TODO,如果是点播,当断网后,将播放进度置为0
		}
	}

	@Override
	public void onNetStatus(Bundle bundle) {

	}

	@Override
	public void onResume() {
		super.onResume();
		stopPlayRtmp();
		if (mPlayerView != null) {
			mPlayerView.onResume();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mPlayerView != null) {
			mPlayerView.onStop();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mLivePlayer != null) {
			mLivePlayer.stopPlay(true);
		}
		if (mPlayerView != null) {
			mPlayerView.onDestroy();
		}
	}
}
