package com.jarvis.library.widget;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午10:42
 * @changeRecord [修改记录] <br/>
 */

public abstract class Presenter implements FacetProvider {

    public static class ViewHolder implements FacetProvider {
        public final View view;
        private Map<Class, Object> mFacets;

        public ViewHolder(View view) {
            this.view = view;
        }

        @Override
        public Object getFacet(Class<?> facetClass) {
            if (mFacets == null) {
                return null;
            }
            return mFacets.get(facetClass);
        }

        public final void setFacet(Class<?> facetClass, Object facetImpl) {
            if (mFacets == null) {
                mFacets = new HashMap<Class, Object>();
            }
            mFacets.put(facetClass, facetImpl);
        }
    }

    private Map<Class, Object> mFacets;

    public abstract ViewHolder onCreateViewHolder(ViewGroup parent);

    public abstract void onBindViewHolder(ViewHolder viewHolder, Object item);

    public abstract void onUnBindViewHolder(ViewHolder viewHolder);

    public void onViewAttachedToWindow(ViewHolder viewHolder) {

    }

    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        cancelAnimationsRecursive(viewHolder.view);
    }

    protected static void cancelAnimationsRecursive(View view) {
        if (view != null && view.hasTransientState()) {
            view.animate().cancel();
            if (view instanceof ViewGroup) {
                final int count = ((ViewGroup) view).getChildCount();
                for (int i = 0; view.hasTransientState() && i < count; i++) {
                    cancelAnimationsRecursive(((ViewGroup) view).getChildAt(i));
                }
            }
        }
    }

    public void setOnClickListener(ViewHolder viewHolder, View.OnClickListener listener) {
        viewHolder.view.setOnClickListener(listener);
    }

    @Override
    public Object getFacet(Class<?> facetClass) {
        if (mFacets == null) {
            return null;
        }
        return mFacets.get(facetClass);
    }

    public final void setFacet(Class<?> facetClass, Object facetImpl) {
        if (mFacets == null) {
            mFacets = new HashMap<Class, Object>();
        }
        mFacets.put(facetClass, facetImpl);
    }
}
