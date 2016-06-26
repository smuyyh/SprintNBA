package com.zengcanxiang.baseAdapter.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zengcanxiang.baseAdapter.interFace.DataHelper;

import java.util.List;

/**
 * 提供便捷操作的baseAdapter
 *
 * @author zengcx
 */
public abstract class HelperAdapter<T> extends BaseAdapter<T>
        implements DataHelper<T> {
    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public HelperAdapter(List<T> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    public BH onCreateViewHolder(ViewGroup parent, int viewType) {
        HelperViewHolder holder;
        if (viewType < 0 || viewType > mLayoutId.length) {
            //throw new ArrayIndexOutOfBoundsException("checkLayoutIndex > LayoutId.length");
        }
        if (mLayoutId.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = mLayoutId[viewType];
        View view = inflateItemView(layoutId, parent);
        holder = (HelperViewHolder) view.getTag();
        if (holder == null || holder.getLayoutId() != layoutId) {
            holder = new HelperViewHolder(mContext, layoutId, view);
        }
        return holder;
    }


    @Override
    protected void onBindData(BH viewHolder, int position, T item) {
        HelperViewHolder helperViewHolder = (HelperViewHolder) viewHolder;

        HelperBindData(helperViewHolder, position, item);
        
        //赋值相关事件,例如点击长按等
        setListener(helperViewHolder, position, item);
    }

    protected abstract void HelperBindData(HelperViewHolder viewHolder, int position, T item);

    /**
     * 绑定相关事件,例如点击长按等,默认空实现
     *
     * @param viewHolder
     * @param position   数据的位置
     * @param item       数据项
     */
    protected void setListener(HelperViewHolder viewHolder, int position, T item) {

    }


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
        return getItemCount() == 0 ? null : mList.get(index);
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
