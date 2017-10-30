package com.jarvis.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午11:24
 * @changeRecord [修改记录] <br/>
 */

public class ZhihuRecyclerView extends RecyclerView {

    protected ObjectAdapter mObjectAdapter;
    protected PresenterSelector mPresenterSelector;
    private ItemBridgeAdapter mItemBridgeAdapter;

    protected boolean isDispatchDrawOrder = true;

    public ZhihuRecyclerView(Context context) {
        super(context);
    }

    public ZhihuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZhihuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isDispatchDrawOrder() {
        return this.isDispatchDrawOrder;
    }

    public void setDispatchDrawOrder(boolean isDispatchDrawOrder) {
        this.isDispatchDrawOrder = isDispatchDrawOrder;
        this.invalidate();
    }

    public void setObjectAdapter(ObjectAdapter adapter) {
        this.mObjectAdapter = adapter;
        this.updateAdapter();
    }

    public void notifyObjectAdapterChanged(ObjectAdapter adapter) {
        if(this.mItemBridgeAdapter != null) {
            this.mItemBridgeAdapter.setAdapter(adapter);
        }

    }

    private void updateAdapter() {

        if(this.mItemBridgeAdapter != null) {
            this.mItemBridgeAdapter.clear();
            this.mItemBridgeAdapter = null;
        }

        if(this.mObjectAdapter != null) {
            this.mItemBridgeAdapter = new ItemBridgeAdapter(this.mObjectAdapter,
                    this.mPresenterSelector == null ? this.mObjectAdapter.getPresenterSelector() : this.mPresenterSelector) {
                @Override
                protected void onCreate(ViewHolder viewHolder) {
                    super.onCreate(viewHolder);

                }

                @Override
                public int getItemViewType(int position) {
                    int type = super.getItemViewType(position);
                    return type;
                }
            };
        }

        this.setAdapter(mItemBridgeAdapter);
    }



}
