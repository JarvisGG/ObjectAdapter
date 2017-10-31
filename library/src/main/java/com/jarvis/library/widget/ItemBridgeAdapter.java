package com.jarvis.library.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午11:26
 * @changeRecord [修改记录] <br/>
 */

public class ItemBridgeAdapter extends RecyclerView.Adapter implements FacetProviderAdapter {

    private static final String TAG = "ItemBridgeAdapter";
    private static final boolean DEBUG = false;

    public static class AdapterListener {
        public void onAddPresenter(Presenter presenter, int type) {

        }

        public void onCreate(ViewHolder viewHolder) {

        }

        public void onBind(ViewHolder viewHolder) {

        }

        public void onUnBind(ViewHolder viewHolder) {

        }

        public void onAttachedToWindow(ViewHolder viewHolde) {

        }

        public void onDetachedFromWindow(ViewHolder viewHolder) {

        }
    }

    /**
     * 装饰view
     */
    public static abstract class Wrapper {
        public abstract View createWrapper(View root);
        public abstract void wrap(View wrapper, View wrapped);
    }

    private ObjectAdapter mAdapter;
    private Wrapper mWrapper;
    private PresenterSelector mPresenterSelector;
    private AdapterListener mAdapterListener;
    private ArrayList<Presenter> mPresenters = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder implements FacetProvider {
        final Presenter mPresenter;
        final Presenter.ViewHolder mHolder;
        Object mItem;
        Object mExtraObject;

        public final Presenter getPresenter() {
            return mPresenter;
        }

        public final Presenter.ViewHolder getViewHolder() {
            return mHolder;
        }

        public final Object getItem() {
            return mItem;
        }

        public final Object getExtraObject() {
            return mExtraObject;
        }

        public void setExtraObject(Object object) {
            mExtraObject = object;
        }

        @Override
        public Object getFacet(Class<?> facetClass) {
            return mHolder.getFacet(facetClass);
        }


        public ViewHolder(View itemView, Presenter mPresenter, Presenter.ViewHolder mHolder) {
            super(itemView);
            this.mPresenter = mPresenter;
            this.mHolder = mHolder;
        }

    }

    private ObjectAdapter.DataObserver mDataObserver = new ObjectAdapter.DataObserver() {
        @Override
        public void onChanged() {
            ItemBridgeAdapter.this.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            ItemBridgeAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            ItemBridgeAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            ItemBridgeAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition) {
            ItemBridgeAdapter.this.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public ItemBridgeAdapter(ObjectAdapter adapter, PresenterSelector presenterSelector) {
        setAdapter(adapter);
        mPresenterSelector = presenterSelector;
    }

    public ItemBridgeAdapter(ObjectAdapter adapter) {
        this(adapter, null);
    }

    public ItemBridgeAdapter() {
    }

    public void setAdapter(ObjectAdapter adapter) {
        if (adapter == mAdapter) {
            return;
        }
        if (mAdapter != null) {
            mAdapter.unregisterObserver(mDataObserver);
        }
        mAdapter = adapter;
        if (mAdapter == null) {
            notifyDataSetChanged();
            return;
        }

        mAdapter.registerObserver(mDataObserver);
        if (hasObservers() != mAdapter.hasStableIds()) {
            setHasStableIds(mAdapter.hasStableIds());
        }
        notifyDataSetChanged();
    }

    public void setWrapper(Wrapper wrapper) {
        mWrapper = wrapper;
    }

    public Wrapper getWrapper() {
        return mWrapper;
    }

    public void clear() {
        setAdapter(null);
    }

    public void setPresenterMapper(ArrayList<Presenter> presenters) {
        mPresenters = presenters;
    }

    public ArrayList<Presenter> getPresenterMapper() {
        return mPresenters;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Presenter presenter = mPresenters.get(viewType);
        Presenter.ViewHolder presenterVh;
        View view;
        if (mWrapper != null) {
            view = mWrapper.createWrapper(parent);
            presenterVh = presenter.onCreateViewHolder(parent);
            mWrapper.wrap(view, presenterVh.view);
        } else {
            presenterVh = presenter.onCreateViewHolder(parent);
            view = presenterVh.view;
        }
        ViewHolder viewHolder = new ViewHolder(view, presenter, presenterVh);
        onCreate(viewHolder);
        if (mAdapterListener != null) {
            mAdapterListener.onCreate(viewHolder);
        }
        View presenterView = viewHolder.mHolder.view;
        if (presenterView != null) {

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mItem = mAdapter.get(position);
        viewHolder.mPresenter.onBindViewHolder(viewHolder.mHolder, viewHolder.mItem, position);
        onBind(viewHolder);
        if (mAdapterListener != null) {
            mAdapterListener.onBind(viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter != null ? mAdapter.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        PresenterSelector presenterSelector = mPresenterSelector != null ?
                mPresenterSelector : mAdapter.getPresenterSelector();
        Object item = mAdapter.get(position);
        Presenter presenter = presenterSelector.getPresenter(item);
        int type = mPresenters.indexOf(presenter);
        if (type < 0) {
            mPresenters.add(presenter);
            type = mPresenters.indexOf(presenter);
            onAddPresenter(presenter, type);
            if (mAdapterListener != null) {
                mAdapterListener.onAddPresenter(presenter, type);
            }
        }
        return type;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mPresenter.onUnBindViewHolder(viewHolder.mHolder);
        onUnbind(viewHolder);
        if (mAdapterListener != null) {
            mAdapterListener.onUnBind(viewHolder);
        }
        viewHolder.mItem = null;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        onAttachedToWindow(viewHolder);
        if (mAdapterListener != null) {
            mAdapterListener.onAttachedToWindow(viewHolder);
        }
        viewHolder.mPresenter.onViewAttachedToWindow(viewHolder.mHolder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mPresenter.onViewDetachedFromWindow(viewHolder.mHolder);
        onDetachedFromWindow(viewHolder);
        if (mAdapterListener != null) {
            mAdapterListener.onDetachedFromWindow(viewHolder);
        }
    }

    protected void onAddPresenter(Presenter presenter, int type) {
    }

    protected void onCreate(ViewHolder viewHolder) {
    }

    protected void onBind(ViewHolder viewHolder) {
    }

    protected void onUnbind(ViewHolder viewHolder) {
    }

    protected void onAttachedToWindow(ViewHolder viewHolder) {
    }

    protected void onDetachedFromWindow(ViewHolder viewHolder) {
    }


    @Override
    public long getItemId(int position) {
        return mAdapter.getId(position);
    }

    @Override
    public FacetProvider getFacetProvider(int type) {
        return mPresenters.get(type);
    }

    public void setAdapterListener(AdapterListener listener) {
        this.mAdapterListener = listener;
    }
}
