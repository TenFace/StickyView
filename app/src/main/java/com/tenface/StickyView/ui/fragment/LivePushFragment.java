package com.tenface.StickyView.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tenface.StickyView.R;

/**
 * Created by ccy on 2016/9/18.
 */
public class LivePushFragment extends Fragment implements ITXLivePushListener, SeekBar.OnSeekBarChangeListener {


	private TXCloudVideoView mCaptureView;
	private Button mBtnPlay;
	private TXLivePusher mPusher;
	private TXLivePushConfig mConfig;
	private Button mBtnCameraChange;
	private Button mBtnTouchFocus;
	private Button mBtnFlash;
	private LinearLayout mFaceBeautyLayout;
	private SeekBar mBeautySeekBar;
	private Button mBtnFaceBeauty;
	private SeekBar mWhiteningSeekBar;
	private Button mBtnBitrate;
	private LinearLayout mBitrateLayout;
	private RadioGroup mRadiogroupBitrate;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_live_push, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init(view);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFaceBeautyLayout.setVisibility(View.GONE);
				mBitrateLayout.setVisibility(View.GONE);
			}
		});
	}

	private void init(View view) {
		initView(view);
		initData();
		initListener();
	}

	private boolean mVideoPublish;

	private void initListener() {
		mBtnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mVideoPublish) {
					//1,修正码率
					fitOrAdjustBitrate(R.id.radio_btn_auto);
					//2,开始推流
					startPublishRtmp();
					//3,将状态置为true
					mVideoPublish = true;
				} else {
					stopPublishRtmp();
					mVideoPublish = false;
				}
			}
		});

		mBtnCameraChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFrontCamera = !mFrontCamera;
				//判断是否在推送中
				//1,如果在推送中,则直接切换摄像头
				//2,否则修改摄像头使用配置
				if (mPusher.isPushing()) {
					mPusher.switchCamera();
				} else {
					mConfig.setFrontCamera(mFrontCamera);
				}
				mBtnCameraChange.setBackgroundResource(mFrontCamera ? R.drawable.camera_change : R.drawable.camera_change2);
			}
		});

		mBtnTouchFocus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//前置摄像头不支持对焦
				if (mFrontCamera) {
					return;
				}
				mTouchFocus = !mTouchFocus;
				mConfig.setTouchFocus(mTouchFocus);
				mBtnTouchFocus.setBackgroundResource(mTouchFocus ? R.drawable.manual : R.drawable.automatic);
				//判断如果处于推送中
				//对摄像头进行语言效果处理
				if (mPusher.isPushing()) {
					mPusher.stopCameraPreview(false);
					mPusher.startCameraPreview(mCaptureView);
				}
				Toast.makeText(getActivity(), mTouchFocus ? "开启手动对焦" : "开启自动对焦", Toast.LENGTH_SHORT).show();
			}
		});

		mBtnFlash.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFlashTurnOn = !mFlashTurnOn;

				//如果打开失败,则有两个原因,要提示用户
				//1,前置摄像头不支持闪光灯
				//2,根本没有开启摄像头
				if (!mPusher.turnOnFlashLight(mFlashTurnOn)) {
					Toast.makeText(getActivity(), "未正常打开闪光灯:1,前置摄像头不支持闪光灯.2,没有开启摄像头", Toast.LENGTH_SHORT).show();
				}

				mBtnFlash.setBackgroundResource(mFlashTurnOn ? R.drawable.flash_on : R.drawable.flash_off);
			}
		});

		mBtnFaceBeauty.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFaceBeautyLayout.setVisibility(View.VISIBLE);
			}
		});

		mBeautySeekBar.setOnSeekBarChangeListener(this);
		mWhiteningSeekBar.setOnSeekBarChangeListener(this);

		mBtnBitrate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mBitrateLayout.setVisibility(View.VISIBLE);
			}
		});

		mRadiogroupBitrate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fitOrAdjustBitrate(checkedId);
				mBitrateLayout.setVisibility(View.GONE);
			}
		});

	}

	private boolean mFlashTurnOn;

	private boolean mTouchFocus = true;

	private boolean mFrontCamera = true;


	private void startPublishRtmp() {
		//1,定义推流的地址
		String rtmpUrl = "rtmp://2000.livepush.myqcloud.com/live/2000_4eb4da7079af11e69776e435c87f075e?bizid=2000";
		//2,设置VideoView显示
		mCaptureView.setVisibility(View.VISIBLE);
		mPusher.setPushListener(this);
		//开启摄像头,将摄像头获取到的数据显示到VideoView上
		mPusher.startCameraPreview(mCaptureView);
		//开始推流
		//参数:推流地址
		mPusher.startPusher(rtmpUrl);
		mBtnPlay.setBackgroundResource(R.drawable.play_pause);
	}

	private void fitOrAdjustBitrate(int checkedId) {
		switch (checkedId) {
			case R.id.radio_btn_fix_720p:
				if(mPusher!=null){
					mConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280);
					mConfig.setAutoAdjustBitrate(false);
					mConfig.setVideoBitrate(1500);
					mPusher.setConfig(mConfig);
				}
				mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
				break;
			case R.id.radio_btn_fix_540p:
				if(mPusher!=null){
					mConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960);
					mConfig.setAutoAdjustBitrate(false);
					mConfig.setVideoBitrate(1000);
					mPusher.setConfig(mConfig);
				}
				mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
				break;
			case R.id.radio_btn_fix_360p:
				if(mPusher!=null){
					mConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
					mConfig.setAutoAdjustBitrate(false);
					mConfig.setVideoBitrate(700);
					mPusher.setConfig(mConfig);
				}
				mBtnBitrate.setBackgroundResource(R.drawable.fix_bitrate);
				break;
			case R.id.radio_btn_auto:
				if (mPusher != null) {
					//设置界面画质
					mConfig.setVideoResolution(TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640);
					//设置开启自适应码率
					mConfig.setAutoAdjustBitrate(true);
					//设置最大码率
					mConfig.setMaxVideoBitrate(1000);
					//设置最小码率
					mConfig.setMinVideoBitrate(500);
					//设置常规码率
					mConfig.setVideoBitrate(700);
					mPusher.setConfig(mConfig);
				}
				mBtnBitrate.setBackgroundResource(R.drawable.auto_bitrate);
				break;
		}


	}

	private void initData() {
		//初始化推送器
		mPusher = new TXLivePusher(getActivity());
		//初始化推送配置
		mConfig = new TXLivePushConfig();
	}

	private void initView(View view) {
		mCaptureView = (TXCloudVideoView) view.findViewById(R.id.video_view);
		mBtnPlay = (Button) view.findViewById(R.id.btnPlay);

		mBtnCameraChange = (Button) view.findViewById(R.id.btnCameraChange);

		mBtnTouchFocus = (Button) view.findViewById(R.id.btnTouchFocus);

		mBtnFlash = (Button) view.findViewById(R.id.btnFlash);

		mFaceBeautyLayout = (LinearLayout) view.findViewById(R.id.layoutFaceBeauty);
		mWhiteningSeekBar = (SeekBar) view.findViewById(R.id.whitening_seekbar);
		mBeautySeekBar = (SeekBar) view.findViewById(R.id.beauty_seekbar);
		mBtnFaceBeauty = (Button) view.findViewById(R.id.btnFaceBeauty);

		mBtnBitrate = (Button) view.findViewById(R.id.btnBitrate);
		mBitrateLayout = (LinearLayout) view.findViewById(R.id.layoutBitrate);
		mRadiogroupBitrate = (RadioGroup) view.findViewById(R.id.resolutionRadioGroup);
	}

	@Override
	public void onPushEvent(int event, Bundle param) {
		//如果网络断开,则停止推流
		if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT) {
			mVideoPublish = false;
			stopPublishRtmp();
		}
	}

	//停止推流
	private void stopPublishRtmp() {
		//关闭摄像头
		//参数:关闭摄像头后,会残留最后一张图片,true则删除最后一张图片残留
		mPusher.stopCameraPreview(true);
		mPusher.setPushListener(null);
		mCaptureView.setVisibility(View.GONE);
		mBtnPlay.setBackgroundResource(R.drawable.play_start);
	}

	@Override
	public void onNetStatus(Bundle bundle) {

	}

	@Override
	public void onResume() {
		super.onResume();
		if (mCaptureView != null) {
			mCaptureView.onResume();
		}

		if (mVideoPublish && !mPusher.isPushing()) {
			mPusher.startCameraPreview(mCaptureView);
		}


	}

	@Override
	public void onStop() {
		super.onStop();
		if (mCaptureView != null) {
			mCaptureView.onStop();
		}
		//关闭摄像头,留有最后一张残留图片
		mPusher.stopCameraPreview(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mCaptureView != null) {
			mCaptureView.onDestroy();
		}
	}

	private int mBeautyLevel;
	private int mWhiteningLevel;

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
			case R.id.beauty_seekbar:
				mBeautyLevel = progress;
				break;
			case R.id.whitening_seekbar:
				mWhiteningLevel = progress;

				break;
		}
		if (mPusher != null) {
			//进行美颜美白处理
			boolean isBeauty = mPusher.setBeautyFilter(mBeautyLevel, mWhiteningLevel);
			if (!isBeauty) {
				Toast.makeText(getActivity(), "当前记性不支持美颜", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}
}
