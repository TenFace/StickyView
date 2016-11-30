package com.tenface.StickyView.model;

import java.util.Comparator;

/**
 * Created by tenface on 16/11/30.
 */
public class TravelingEntityComparator implements Comparator<TravelingEntity> {

    @Override
    public int compare(TravelingEntity lhs, TravelingEntity rhs) {
        return rhs.getRank() - lhs.getRank();
    }
}
