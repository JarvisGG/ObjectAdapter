package com.jarvis.library.widget;

import android.database.Observable;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午10:30
 * @changeRecord [修改记录] <br/>
 */

public abstract class ObjectAdapter {
    public static final int NO_ID = -1;

    public static abstract class DataObserver {
        public void onChanged() {}

        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }
    }

    private static final class DataObservable extends Observable<DataObserver> {

        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeChanged(positionStart, itemCount);
            }
        }

        public void notifyItemRangeInserted(int positionStart, int itemCount) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeInserted(positionStart, itemCount);
            }
        }

        public void notifyItemRangeRemoved(int positionStart, int itemCount) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onItemRangeRemoved(positionStart, itemCount);
            }
        }
    }

    private final DataObservable mObservable = new DataObservable();
    private boolean mHasStableIds;
    private PresenterSelector mPresenterSelector;

    public ObjectAdapter(PresenterSelector presenterSelector) {
        setPresenterSelector(presenterSelector);
    }

    public ObjectAdapter(Presenter presenter) {
        setPresenterSelector(new SinglePresenterSelector(presenter));
    }

    public ObjectAdapter() {
    }

    public final void setPresenterSelector(PresenterSelector presenterSelector) {
        if (presenterSelector == null) {
            throw new IllegalArgumentException("Presenter selector must not be null");
        }
        final boolean update = (mPresenterSelector != null);
        final boolean selectorChanged = update && mPresenterSelector != presenterSelector;

        mPresenterSelector = presenterSelector;

        if (selectorChanged) {
            onPresenterSelectorChanged();
        }

        if (update) {
            notifyChanged();
        }
    }

    protected void onPresenterSelectorChanged() {}

    public final PresenterSelector getPresenterSelector() {
        return mPresenterSelector;
    }

    public final void registerObserver(DataObserver observer) {
        mObservable.registerObserver(observer);
    }

    public final void unregisterObserver(DataObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    public final void unregisterAllObservers() {
        mObservable.unregisterAll();
    }

    final protected void notifyItemRangeChanged(int positionStart, int itemCount) {
        mObservable.notifyItemRangeChanged(positionStart, itemCount);
    }

    final protected void notifyItemRangeInserted(int positionStart, int itemCount) {
        mObservable.notifyItemRangeInserted(positionStart, itemCount);
    }

    final protected void notifyItemRangeRemoved(int positionStart, int itemCount) {
        mObservable.notifyItemRangeRemoved(positionStart, itemCount);
    }

    final protected void notifyChanged() {
        mObservable.notifyChanged();
    }

    public final boolean hasStableIds() {
        return mHasStableIds;
    }

    public final void setHasStableIds(boolean hasStableIds) {
        boolean changed = mHasStableIds != hasStableIds;
        mHasStableIds = hasStableIds;

        if (changed) {
            onHasStableIdsChanged();
        }
    }

    protected void onHasStableIdsChanged() {

    }

    public final Presenter getPresenter(Object item) {
        if (mPresenterSelector == null) {
            throw new IllegalStateException("Presenter selector must not be null");
        }
        return mPresenterSelector.getPresenter(item);
    }

    public abstract int size();

    public abstract Object get(int position);

    public long getId(int position) {
        return NO_ID;
    }

}
