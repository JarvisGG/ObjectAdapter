package com.jarvis.library.widget;

/**
 * @author Jarvis @ Zhihu Inc.
 * @version 1.0
 * @title ObjectAdapter
 * @description 该类主要功能描述
 * @create 2017/10/23 上午10:44
 * @changeRecord [修改记录] <br/>
 */

public interface FacetProvider {

    public Object getFacet(Class<?> facetClass);
}
