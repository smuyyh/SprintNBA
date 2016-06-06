package com.zengcanxiang.baseAdapter.expandableListView;

import android.content.Context;

import com.zengcanxiang.baseAdapter.absListView.BaseViewHolder;
import com.zengcanxiang.baseAdapter.absListView.HelperViewHolder;

import java.util.List;

public abstract class HelperAdapter<T> extends BaseAdapter<T> {

    public HelperAdapter(List<List<T>> data, Context context, int[] groupLayoutIds, int... childLayoutIds) {
        super(data, context, groupLayoutIds, childLayoutIds);
    }

    @Override
    public <BH extends BaseViewHolder> void convertGroup(BH viewHolder, int groupPosition, List<T> childs) {
        HelperViewHolder holder = (HelperViewHolder) viewHolder;
        HelpConvertGroup(holder, groupPosition, childs);
    }

    @Override
    public <BH extends BaseViewHolder> void convertChild(BH viewHolder, int groupPosition, int childPosition, T t) {
        HelperViewHolder holder = (HelperViewHolder) viewHolder;
        HelpConvertChild(holder, groupPosition, childPosition, t);
    }

    /**
     * <p>实现具体控件的获取和赋值等业务</p>
     */
    public abstract void HelpConvertGroup(HelperViewHolder viewHolder, int groupPosition, List<T> childs);

    /**
     * <p>实现具体控件的获取和赋值等业务</p>
     */
    public abstract void HelpConvertChild(HelperViewHolder viewHolder, int groupPosition, int childPosition, T t);


    //几个方法
    //一组数据的增删改
    //某组单个数据的增删改
    //判断是否有数据
    //判断数据是否存在改list
    //添加数据到不同的位置
    //
    //
    //
    //
}
