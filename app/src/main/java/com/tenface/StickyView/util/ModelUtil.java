package com.tenface.StickyView.util;

import com.tenface.StickyView.R;
import com.tenface.StickyView.model.ChannelEntity;
import com.tenface.StickyView.model.FilterEntity;
import com.tenface.StickyView.model.FilterTwoEntity;
import com.tenface.StickyView.model.OperationEntity;
import com.tenface.StickyView.model.TravelingEntity;
import com.tenface.StickyView.model.TravelingEntityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tenface on 16/11/30.
 */
public class ModelUtil {

    public static final String type_scenery = "风景";
    public static final String type_building = "建筑";
    public static final String type_animal = "动物";
    public static final String type_plant = "植物";

    // 广告数据
    public static List<String> getAdData() {
        List<String> adList = new ArrayList<>();
        adList.add("https://github.com/TenFace/StickyViewImage/blob/master/jingse.jpg?raw=true");
        adList.add("https://github.com/TenFace/StickyViewImage/blob/master/loushang.jpg?raw=true");
        adList.add("https://github.com/TenFace/StickyViewImage/blob/master/xiaozhang.jpg?raw=true");
        return adList;
    }

    // 频道数据
    public static List<ChannelEntity> getChannelData() {
        List<ChannelEntity> channelList = new ArrayList<>();
        channelList.add(new ChannelEntity("每次", "我", "https://github.com/TenFace/StickyViewImage/blob/master/icon1.png?raw=true"));
        channelList.add(new ChannelEntity("过年", "爱", "https://github.com/TenFace/StickyViewImage/blob/master/icon2.png?raw=true"));
        channelList.add(new ChannelEntity("都会", "你", "https://github.com/TenFace/StickyViewImage/blob/master/icon3.png?raw=true"));
        channelList.add(new ChannelEntity("想起", "我", "https://github.com/TenFace/StickyViewImage/blob/master/icon4.png?raw=true"));
        channelList.add(new ChannelEntity("我的家", "的", "https://github.com/TenFace/StickyViewImage/blob/master/icon5.png?raw=true"));
        channelList.add(new ChannelEntity("我的家人", "家", "https://github.com/TenFace/StickyViewImage/blob/master/icon6.png?raw=true"));
        return channelList;
    }

    // 运营数据
    public static List<OperationEntity> getOperationData() {
        List<OperationEntity> operationList = new ArrayList<>();
        operationList.add(new OperationEntity("我的家", "幸福的港湾", "https://github.com/TenFace/StickyViewImage/blob/master/icon9.jpg?raw=true"));
        operationList.add(new OperationEntity("一家人", "一生的守候", "https://github.com/TenFace/StickyViewImage/blob/master/icon7.png?raw=true"));
        return operationList;
    }

    // ListView数据
    public static List<TravelingEntity> getTravelingData() {
        List<TravelingEntity> travelingList = new ArrayList<>();
        travelingList.add(new TravelingEntity(type_scenery, "大理", "中国", 1, "http://img5.imgtn.bdimg.com/it/u=2769726205,1778838650&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "", "西班牙", 20, "http://img1.imgtn.bdimg.com/it/u=1832737924,144748431&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "", "意大利", 21, "http://img5.imgtn.bdimg.com/it/u=2091366266,1524114981&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "拱门", "美国", 5, "http://img4.imgtn.bdimg.com/it/u=3673198446,2175517238&fm=206&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_plant, "荷花", "中国", 4, "http://img4.imgtn.bdimg.com/it/u=3052089044,3887933556&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "", "西班牙", 18, "http://img2.imgtn.bdimg.com/it/u=140083303,1086773509&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "", "西班牙", 19, "http://img5.imgtn.bdimg.com/it/u=1424970962,1243597989&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_animal, "水貂", "美国", 7, "http://img4.imgtn.bdimg.com/it/u=1387833256,3665925904&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_plant, "仙人掌", "美国", 8, "http://img1.imgtn.bdimg.com/it/u=3808801622,1608105009&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "威尔士", "英国", 9, "http://img4.imgtn.bdimg.com/it/u=2440866214,1867472386&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "伦敦塔桥", "英国", 10, "http://img3.imgtn.bdimg.com/it/u=3040385967,1031044866&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_animal, "", "英国", 11, "http://img3.imgtn.bdimg.com/it/u=1896821840,3837942977&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_plant, "", "英国", 12, "http://img3.imgtn.bdimg.com/it/u=2745651862,279304559&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "", "德国", 13, "http://img3.imgtn.bdimg.com/it/u=4137420324,1489843447&fm=206&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "自由女神像", "美国", 6, "http://img3.imgtn.bdimg.com/it/u=2566161363,1140447270&fm=206&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "拉萨", "中国", 2, "http://img1.imgtn.bdimg.com/it/u=372954611,2699392190&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_animal, "熊猫", "中国", 3, "http://img0.imgtn.bdimg.com/it/u=1022702848,645282860&fm=206&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "", "德国", 14, "http://img1.imgtn.bdimg.com/it/u=3436675019,2609348874&fm=206&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_animal, "狗熊", "德国", 15, "http://img4.imgtn.bdimg.com/it/u=4280994062,276434784&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_plant, "", "德国", 16, "http://img0.imgtn.bdimg.com/it/u=1644854868,3172549858&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_scenery, "", "西班牙", 17, "http://img4.imgtn.bdimg.com/it/u=620137884,621556624&fm=21&gp=0.jpg"));
        travelingList.add(new TravelingEntity(type_building, "", "意大利", 22, "http://img0.imgtn.bdimg.com/it/u=3631118072,1530723002&fm=206&gp=0.jpg"));
        return travelingList;
    }

    // 分类数据
    public static List<FilterTwoEntity> getCategoryData() {
        List<FilterTwoEntity> list = new ArrayList<>();
        list.add(new FilterTwoEntity(type_scenery, getFilterData()));
        list.add(new FilterTwoEntity(type_building, getFilterData()));
        list.add(new FilterTwoEntity(type_animal, getFilterData()));
        list.add(new FilterTwoEntity(type_plant, getFilterData()));
        return list;
    }

    // 排序数据
    public static List<FilterEntity> getSortData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("排序从高到低", "1"));
        list.add(new FilterEntity("排序从低到高", "2"));
        return list;
    }

    // 筛选数据
    public static List<FilterEntity> getFilterData() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("中国", "1"));
        list.add(new FilterEntity("美国", "2"));
        list.add(new FilterEntity("英国", "3"));
        list.add(new FilterEntity("德国", "4"));
        list.add(new FilterEntity("西班牙", "5"));
        list.add(new FilterEntity("意大利", "6"));
        return list;
    }

    // ListView分类数据
    public static List<TravelingEntity> getCategoryTravelingData(FilterTwoEntity entity) {
        List<TravelingEntity> list = getTravelingData();
        List<TravelingEntity> travelingList = new ArrayList<>();
        int size = list.size();
        for (int i=0; i<size; i++) {
            if (list.get(i).getType().equals(entity.getType()) &&
                    list.get(i).getFrom().equals(entity.getSelectedFilterEntity().getKey())) {
                travelingList.add(list.get(i));
            }
        }
        return travelingList;
    }

    // ListView排序数据
    public static List<TravelingEntity> getSortTravelingData(FilterEntity entity) {
        List<TravelingEntity> list = getTravelingData();
        Comparator<TravelingEntity> ascComparator = new TravelingEntityComparator();
        if (entity.getKey().equals("排序从高到低")) {
            Collections.sort(list);
        } else {
            Collections.sort(list, ascComparator);
        }
        return list;
    }

    // ListView筛选数据
    public static List<TravelingEntity> getFilterTravelingData(FilterEntity entity) {
        List<TravelingEntity> list = getTravelingData();
        List<TravelingEntity> travelingList = new ArrayList<>();
        int size = list.size();
        for (int i=0; i<size; i++) {
            if (list.get(i).getFrom().equals(entity.getKey())) {
                travelingList.add(list.get(i));
            }
        }
        return travelingList;
    }

    // 暂无数据
    public static List<TravelingEntity> getNoDataEntity(int height) {
        List<TravelingEntity> list = new ArrayList<>();
        TravelingEntity entity = new TravelingEntity();
        entity.setNoData(true);
        entity.setHeight(height);
        list.add(entity);
        return list;
    }

}
