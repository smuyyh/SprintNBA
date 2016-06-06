package com.zengcanxiang.baseAdapter.absListView;

import android.content.Context;

import com.zengcanxiang.baseAdapter.interFace.DataHelper;

import java.util.List;

/**
 * 提供便捷操作的baseAdapter
 *
 * @author zengcx
 */
public abstract class HelperAdapter<T> extends BaseAdapter<T> implements DataHelper<T> {

    public HelperAdapter(List<T> mList, Context context, int... layoutIds) {
        super(mList, context, layoutIds);
    }

    @Deprecated
    public HelperAdapter(List<T> mList, Context context) {
        super(mList, context);
    }

    @Override
    public <BH extends BaseViewHolder> void convert(BH viewHolder, int position, T t) {
        HelperViewHolder holder = (HelperViewHolder) viewHolder;
        HelpConvert(holder, position, t);
    }

    /**
     * <p>实现具体控件的获取和赋值等业务</p>
     */
    public abstract void HelpConvert(HelperViewHolder viewHolder, int position, T t);

    @Override
    public boolean isEnabled(int position) {
        return position < mList.size();
    }

    @Override
    public void addItemToHead(T data) {
        add(0, data);
    }


    @Override
    public boolean addItemToLast(T data) {
        boolean result = mList.add(data);
        notifyDataSetChanged();
        return result;
    }


    @Override
    public boolean addItemsToHead(List<T> datas) {
        return addAll(0, datas);
    }


    @Override
    public boolean addItemsToLast(List<T> datas) {
        return mList.addAll(datas);
    }


    @Override
    public boolean addAll(int startPosition, List<T> datas) {
        boolean result = mList.addAll(startPosition, datas);
        notifyDataSetChanged();
        return result;
    }


    @Override
    public void add(int startPosition, T data) {
        mList.add(startPosition, data);
        notifyDataSetChanged();
    }

    @Override
    public T getData(int index) {
        return getCount() == 0 ? null : mList.get(index);
    }


    @Override
    public void alterObj(T oldData, T newData) {
        alterObj(mList.indexOf(oldData), newData);
    }


    @Override
    public void alterObj(int index, T data) {
        mList.set(index, data);
        notifyDataSetChanged();
    }


    @Override
    public boolean remove(T data) {
        boolean result = mList.remove(data);
        notifyDataSetChanged();
        return result;
    }

    @Override
    public void removeToIndex(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }


    @Override
    public void replaceAll(List<T> data) {
        mList.clear();
        addAll(0, data);
    }

    @Override
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


    @Override
    public boolean contains(T data) {
        return mList.contains(data);
    }


}
