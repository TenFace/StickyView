package com.tenface.StickyView.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tenface.StickyView.R;
import com.tenface.StickyView.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJX .
 */
//轮播图适配器
public class GalleryPagerAdapter extends PagerAdapter {
    private List<String> bannerUrlList=new ArrayList<>();
    private List<String> adList = new ArrayList<>(); // 广告数据
    private Context context;
    public GalleryPagerAdapter(Context context, List<String> adList) {
        this.context = context;
        this.adList = ModelUtil.getAdData();
        bannerUrlList.add(adList.get(0));
        bannerUrlList.add(adList.get(1));
        bannerUrlList.add(adList.get(2));
    }
    @Override
    public int getCount() {
        return bannerUrlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(context);

        Glide.with(context).load(bannerUrlList.get(position)).placeholder(R.drawable.icon_here).
                error(R.drawable.icon_here).diskCacheStrategy(DiskCacheStrategy.ALL).into(item);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);
        final int pos = position;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               ToastUtils.showLong(context, "第" + pos + "张图片被点击了");
//                    Intent intent = new Intent(HouseDetailActivity.this, ImageGalleryActivity.class);
//                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
//                    intent.putExtra("position", pos);
//                    startActivity(intent);
            }
        });

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}