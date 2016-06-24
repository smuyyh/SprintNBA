package com.yuyh.library.view.list.indexablelistview.help;


import com.yuyh.library.view.list.indexablelistview.IndexEntity;

import java.util.Comparator;


/**
 * Created by YoKeyword on 16/3/20.
 */
public class PinyinComparator<T extends IndexEntity> implements Comparator<T> {

    @Override
    public int compare(T lhs, T rhs) {
        return lhs.getFirstSpell().compareTo(rhs.getFirstSpell());
    }
}