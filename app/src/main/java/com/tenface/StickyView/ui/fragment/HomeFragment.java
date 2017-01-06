package com.tenface.StickyView.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tenface.StickyView.R;
import com.tenface.StickyView.adapter.TravelingAdapter;
import com.tenface.StickyView.model.ChannelEntity;
import com.tenface.StickyView.model.FilterData;
import com.tenface.StickyView.model.FilterEntity;
import com.tenface.StickyView.model.FilterTwoEntity;
import com.tenface.StickyView.model.OperationEntity;
import com.tenface.StickyView.model.TravelingEntity;
import com.tenface.StickyView.ui.activity.AboutActivity;
import com.tenface.StickyView.ui.activity.MenuActivity;
import com.tenface.StickyView.util.ColorUtil;
import com.tenface.StickyView.util.DensityUtil;
import com.tenface.StickyView.util.KickBackAnimator;
import com.tenface.StickyView.util.ModelUtil;
import com.tenface.StickyView.view.FilterView;
import com.tenface.StickyView.view.HeaderView.HeaderAdViewView;
import com.tenface.StickyView.view.HeaderView.HeaderChannelViewView;
import com.tenface.StickyView.view.HeaderView.HeaderDividerViewView;
import com.tenface.StickyView.view.HeaderView.HeaderFilterViewView;
import com.tenface.StickyView.view.HeaderView.HeaderOperationViewView;
import com.tenface.StickyView.view.SmoothListView.SmoothListView;
import com.tenface.StickyView.view.SnakeMenuView.TumblrRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TenFace on 2017/1/4.
 */

public class HomeFragment extends BaseFragment implements SmoothListView.ISmoothListViewListener {

    @Bind(R.id.listView)
    SmoothListView smoothListView;
    @Bind(R.id.ll_scroll_page)
    LinearLayout ll_scroll_page;
    @Bind(R.id.fl_container)
    FrameLayout fl_container;
    @Bind(R.id.fv_top_filter)
    FilterView fvTopFilter;
    @Bind(R.id.rl_bar)
    RelativeLayout rlBar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.view_title_bg)
    View viewTitleBg;
    @Bind(R.id.view_action_more_bg)
    View viewActionMoreBg;
    @Bind(R.id.fl_action_more)
    FrameLayout flActionMore;

    private Context mContext;
    protected Activity mActivity;
    private int mScreenHeight; // 屏幕高度
    private Dialog dialog;

    private List<String> adList = new ArrayList<>(); // 广告数据
    private List<ChannelEntity> channelList = new ArrayList<>(); // 频道数据
    private List<OperationEntity> operationList = new ArrayList<>(); // 运营数据
    private List<TravelingEntity> travelingList = new ArrayList<>(); // ListView数据

    private HeaderAdViewView listViewAdHeaderView; // 广告视图
    private HeaderChannelViewView headerChannelView; // 频道视图
    private HeaderOperationViewView headerOperationViewView; // 运营视图
    private HeaderDividerViewView headerDividerViewView; // 分割线占位图
    private HeaderFilterViewView headerFilterViewView; // 分类筛选视图
    private FilterData filterData; // 筛选数据
    private TravelingAdapter mAdapter; // 主页数据

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isScrollIdle = true; // ListView是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 50; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 4; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    private View.OnClickListener menuClickListener;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        initData();
        initView(view);
        initListener();
        return view;
    }


    private void initData() {
        mContext = this.getContext();
        mActivity = this.getActivity();
        mScreenHeight = DensityUtil.getWindowHeight(mActivity);

        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryData());
        filterData.setSorts(ModelUtil.getSortData());
        filterData.setFilters(ModelUtil.getFilterData());

        // 广告数据
        adList = ModelUtil.getAdData();

        // 频道数据
        channelList = ModelUtil.getChannelData();

        // 运营数据
        operationList = ModelUtil.getOperationData();

        // ListView数据
        travelingList = ModelUtil.getTravelingData();
    }

    private void initView(View view) {
        mContext = this.getContext();
        mActivity = this.getActivity();

        fvTopFilter.setVisibility(View.INVISIBLE);

        // 设置筛选数据
        fvTopFilter.setFilterData(mActivity, filterData);

        // 设置广告数据
        listViewAdHeaderView = new HeaderAdViewView(mActivity);
        listViewAdHeaderView.fillView(adList, smoothListView);

        // 设置频道数据
        headerChannelView = new HeaderChannelViewView(mActivity);
        headerChannelView.fillView(channelList, smoothListView);

        // 设置运营数据
        headerOperationViewView = new HeaderOperationViewView(mActivity);
        headerOperationViewView.fillView(operationList, smoothListView);

        // 设置分割线
        headerDividerViewView = new HeaderDividerViewView(mActivity);
        headerDividerViewView.fillView("", smoothListView);

        // 设置筛选数据
        headerFilterViewView = new HeaderFilterViewView(mActivity);
        headerFilterViewView.fillView(new Object(), smoothListView);

        // 设置ListView数据
        mAdapter = new TravelingAdapter(mActivity, travelingList);
        smoothListView.setAdapter(mAdapter);

        filterViewPosition = smoothListView.getHeaderViewsCount() - 1;

        //menu点击事件
        menuClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TFMainActivity.this, "menu click", Toast.LENGTH_LONG).show();
                showDialog();
            }
        };
        TumblrRelativeLayout rootLayout = (TumblrRelativeLayout) view.findViewById(R.id.tumblr_frame_layout);
        rootLayout.setMenuListener(menuClickListener);


    }

    private void initListener() {

        mContext = this.getContext();
        mActivity = this.getActivity();

        // 关于
        flActionMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, AboutActivity.class));
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MenuActivity.class));
            }
        });

        // (假的ListView头部展示的)筛选视图点击
        headerFilterViewView.setOnFilterClickListener(new HeaderFilterViewView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight));
            }
        });

        // (真正的)筛选视图点击
        fvTopFilter.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    fvTopFilter.showFilterLayout(position);
                    if (titleViewHeight - 3 > filterViewTopSpace || filterViewTopSpace > titleViewHeight + 3) {
                        smoothListView.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mContext, titleViewHeight));
                    }
                }
            }
        });

        // 分类Item点击
        fvTopFilter.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity entity) {
                fillAdapter(ModelUtil.getCategoryTravelingData(entity));
            }
        });

        // 排序Item点击
        fvTopFilter.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getSortTravelingData(entity));
            }
        });

        // 筛选Item点击
        fvTopFilter.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                fillAdapter(ModelUtil.getFilterTravelingData(entity));
            }
        });

        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {
            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1 - firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    fvTopFilter.setVisibility(View.INVISIBLE);
                } else {
                    isStickyTop = true; // 吸附在顶部
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    fvTopFilter.setVisibility(View.VISIBLE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    fvTopFilter.showFilterLayout(filterPosition);
                }

                fvTopFilter.setStickyTop(isStickyTop);

                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
    }

    // 填充数据
    public void fillAdapter(List<TravelingEntity> list) {
        if (list == null || list.size() == 0) {
            smoothListView.setLoadMoreEnable(false);
            int height = mScreenHeight - DensityUtil.dip2px(mContext, 95); // 95 = 标题栏高度 ＋ FilterView的高度
            mAdapter.setData(ModelUtil.getNoDataEntity(height));
        } else {
            smoothListView.setLoadMoreEnable(list.size() > TravelingAdapter.ONE_REQUEST_COUNT);
            mAdapter.setData(list);
        }
    }

    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.main_activity_title));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.main_activity_title));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listViewAdHeaderView != null) {
            listViewAdHeaderView.stopADRotate();
        }
    }

    private void showDialog() {
        dialog = new Dialog(mContext, R.style.my_dialog_style);
//        dialog.setCancelable(true);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout, null);
//        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.drawable_rectangle_shape));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.show();
        final ImageView img01 = (ImageView) view.findViewById(R.id.img_01);
        final ImageView img02 = (ImageView) view.findViewById(R.id.img_02);
        final ImageView img03 = (ImageView) view.findViewById(R.id.img_03);
        final ImageView img04 = (ImageView) view.findViewById(R.id.img_04);
        final ImageView img05 = (ImageView) view.findViewById(R.id.img_05);
        final ImageView game = (ImageView) view.findViewById(R.id.img_06);
        // 这几个show和close的操作千万不要用集合循环去操作，否则在显示dialog时会出现dialog闪一下就消失的情况
        showAnim(img01, 100);
        showAnim(img02, 200);
        showAnim(img03, 300);
        showAnim(img04, 400);
        showAnim(img05, 500);
        showAnim(game, 550);
        img01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });
        img02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });
        img03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });
        img04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });
        img05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });

        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim(game, 300, 0);
                closeAnim(img05, 250, 0);
                closeAnim(img04, 200, 0);
                closeAnim(img03, 150, 800);
                closeAnim(img02, 100, 800);
                closeAnim(img01, 50, 800);
            }
        });

    }

    private void showAnim(final ImageView i, int d) {
        i.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                i.setVisibility(View.VISIBLE);
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(i, "translationY", 1000, 0);
                fadeAnim.setDuration(700);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(700);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        i.clearAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }
        }, d);
    }

    private void closeAnim(final ImageView img, int i, int j) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(img, "translationY", 0, 1200);
                fadeAnim.setDuration(700);
                KickBackAnimator kickAnimator = new KickBackAnimator();
                kickAnimator.setDuration(700);
                fadeAnim.setEvaluator(kickAnimator);
                fadeAnim.start();
                fadeAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        img.setVisibility(View.INVISIBLE);
                        img.clearAnimation();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        }, i);
        if (img.getId() == R.id.img_01) {
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, j);
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopLoadMore();
            }
        }, 2000);
    }
}
