package com.tenface.StickyView.view.HeaderView;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.tenface.StickyView.R;
import com.tenface.StickyView.adapter.HeaderOperationAdapter;
import com.tenface.StickyView.model.OperationEntity;
import com.tenface.StickyView.view.FixedGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tenface on 16/11/30.
 */
public class HeaderOperationViewView extends HeaderViewInterface<List<OperationEntity>> {

    @Bind(R.id.gv_operation)
    FixedGridView gvOperation;

    public HeaderOperationViewView(Activity context) {
        super(context);
    }

    @Override
    protected void getView(List<OperationEntity> list, ListView listView) {
        View view = mInflate.inflate(R.layout.header_operation_layout, listView, false);
        ButterKnife.bind(this, view);

        dealWithTheView(list);
        listView.addHeaderView(view);
    }

    private void dealWithTheView(List<OperationEntity> list) {
        HeaderOperationAdapter adapter = new HeaderOperationAdapter(mContext, list);
        gvOperation.setAdapter(adapter);
    }

}
