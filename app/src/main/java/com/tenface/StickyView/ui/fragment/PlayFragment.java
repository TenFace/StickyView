package com.tenface.StickyView.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnCreateContextMenuListener;

import com.tenface.StickyView.R;
import com.tenface.StickyView.model.LiveMenuItemInfo;
import com.tenface.StickyView.ui.activity.LiveActivity;
import com.tenface.StickyView.util.ModelUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TenFace on 2017/1/4.
 */

public class PlayFragment extends BaseFragment {
    @Bind(R.id.ll_image_live)
    LinearLayout ll_image_live;
    @Bind(R.id.lv_menu_live)
    ListView listView;
    private Activity mActivity;
    private Context mContext;
    private Intent intent;

    private List<LiveMenuItemInfo> mlistInfo = new ArrayList<LiveMenuItemInfo>();  //声明一个list，动态存储要显示的信息

    private List<String> urlList = new ArrayList<>();

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, null);
        ButterKnife.bind(this, view);
        initDate();
        initView(view);
        initListener();
        return view;
    }

    private void initDate() {
        mContext = this.getContext();
        mActivity = this.getActivity();
        setInfo();  //给信息赋值函数，用来测试
        listView.setAdapter(new ListViewAdapter(mlistInfo));
    }

    private void initView(View view) {
        mContext = this.getContext();
        mActivity = this.getActivity();
    }

    private void initListener() {
        mContext = this.getContext();
        mActivity = this.getActivity();
        ll_image_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(mActivity, LiveActivity.class);
                mContext.startActivity(intent);
            }
        });

        //处理Item的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveMenuItemInfo getObject = mlistInfo.get(position);   //通过position获取所点击的对象
                int infoId = getObject.getId(); //获取信息id
                String infoTitle = getObject.getTitle();    //获取信息标题
                String infoDetails = getObject.getDetails();    //获取信息详情
                String infoUrl = urlList.get(position);

                //Toast显示测试
//                Toast.makeText(mContext, "信息ID:" + infoId, Toast.LENGTH_SHORT).show();
                intent = new Intent();
                intent.putExtra("infoId",infoId);
                intent.putExtra("infoUrl",infoUrl);
                intent.putExtra("infoTitle",infoTitle);
                intent.putExtra("infoDetails",infoDetails);
                intent.setClass(mActivity,LiveActivity.class);
                mActivity.startActivity(intent);

            }
        });

        //长按菜单显示
        listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu conMenu, View view, ContextMenu.ContextMenuInfo info) {
                conMenu.setHeaderTitle("菜单");
                conMenu.add(0, 0, 0, "条目一");
                conMenu.add(0, 1, 1, "条目二");
                conMenu.add(0, 2, 2, "条目三");
            }
        });
    }

    //长按菜单处理函数
    public boolean onContextItemSelected(MenuItem aItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) aItem.getMenuInfo();
        switch (aItem.getItemId()) {
            case 0:
                Toast.makeText(mContext, "你点击了条目一", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                Toast.makeText(mContext, "你点击了条目二", Toast.LENGTH_SHORT).show();

                return true;
            case 2:
                Toast.makeText(mContext, "你点击了条目三", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    public class ListViewAdapter extends BaseAdapter {
        View[] itemViews;

        public ListViewAdapter(List<LiveMenuItemInfo> mlistInfo) {
            // TODO Auto-generated constructor stub
            itemViews = new View[mlistInfo.size()];
            for (int i = 0; i < mlistInfo.size(); i++) {
                LiveMenuItemInfo getInfo = (LiveMenuItemInfo) mlistInfo.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i] = makeItemView(
                        getInfo.getTitle(), getInfo.getDetails(), getInfo.getAvatar()
                );
            }
        }

        public int getCount() {
            return itemViews.length;
        }

        public View getItem(int position) {
            return itemViews[position];
        }

        public long getItemId(int position) {
            return position;
        }

        //绘制Item的函数
        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.menu_live_listview, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView) itemView.findViewById(R.id.live_title);
            title.setText(strTitle);    //填入相应的值
            TextView text = (TextView) itemView.findViewById(R.id.live_info);
            text.setText(strText);
            ImageView image = (ImageView) itemView.findViewById(R.id.live_item_img);
            image.setImageResource(resId);

            return itemView;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }
    }

    public void setInfo() {
        mlistInfo.clear();
        int i = 0;
        urlList = ModelUtil.getLiveUrl();
        while (i < 10) {
            LiveMenuItemInfo information = new LiveMenuItemInfo();
            information.setId(1000 + i);
            information.setTitle("标题" + i);
            information.setDetails("详细信息" + i);
            information.setAvatar(R.mipmap.user_avatar_default);
            information.setUrl(urlList.get(i));

            mlistInfo.add(information); //将新的info对象加入到信息列表中
            i++;
        }
    }

}
