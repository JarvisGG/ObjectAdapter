package com.jarvis.library.widget;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午10:59
 * @changeRecord [修改记录] <br/>
 */

public final class SinglePresenterSelector extends PresenterSelector {

    private final Presenter mPresenter;

    public SinglePresenterSelector(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public Presenter getPresenter(Object item) {
        return mPresenter;
    }

    @Override
    public Presenter[] getPresenters() {
        return new Presenter[]{mPresenter};
    }
}
