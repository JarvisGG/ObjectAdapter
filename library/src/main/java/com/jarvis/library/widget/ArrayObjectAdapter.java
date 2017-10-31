package com.jarvis.library.widget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午11:13
 * @changeRecord [修改记录] <br/>
 */

public class ArrayObjectAdapter extends ObjectAdapter {

    private ArrayList<Object> mItems = new ArrayList<>();

    public ArrayObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
    }

    public ArrayObjectAdapter(Presenter presenter) {
        super(presenter);
    }

    public ArrayObjectAdapter() {
        super();
    }

    @Override
    public int size() {
        return mItems.size();
    }

    @Override
    public Object get(int position) {
        return mItems.get(position);
    }

    public int indexOf(Object item) {
        return mItems.indexOf(item);
    }

    public void notifyArrayItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    public void add(Object item) {
        add(mItems.size(), item);
    }

    public void add(int index, Object item) {
        mItems.add(index, item);
        notifyItemRangeInserted(index, 1);
    }

    public void addAll(int index, Collection items) {
        int itemCount = items.size();
        if (itemCount == 0) {
            return;
        }
        mItems.addAll(index, items);
        notifyItemRangeInserted(index, itemCount);
    }

    public void move(int fromPosition, int toPosition) {
        notifyItemRangeMoved(fromPosition, toPosition);
    }

    public boolean remove(Object item) {
        int index = mItems.indexOf(item);
        if (index >= 0) {
            mItems.remove(index);
            notifyItemRangeRemoved(index, 1);
        }
        return index >= 0;
    }

    public int removeItems(int position, int count) {
        int itemsToRemove = Math.min(count, mItems.size() - position);
        if (itemsToRemove <= 0) {
            return 0;
        }

        for (int i = 0; i < itemsToRemove; i++) {
            mItems.remove(position);
        }
        notifyItemRangeRemoved(position, itemsToRemove);
        return itemsToRemove;
    }

    public void clear() {
        int itemCount = mItems.size();
        if (itemCount == 0) {
            return;
        }
        mItems.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    public <E> List<E> unmodifiableList() {
        return Collections.unmodifiableList((List<E>) mItems);
    }
}
