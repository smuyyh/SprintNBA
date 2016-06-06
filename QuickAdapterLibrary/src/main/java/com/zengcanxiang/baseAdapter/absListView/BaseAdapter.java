package com.zengcanxiang.baseAdapter.absListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>万能适配Adapter,减少赘于代码和加快开发流程</p>
 *
 * @author zengcx
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mLInflater;
    protected int[] layoutIds;
    private BaseViewHolder holder = new HelperViewHolder();

    /**
     * @param data      数据源
     * @param context   上下文
     * @param layoutIds 布局Id
     */
    public BaseAdapter(List<T> data, Context context, int... layoutIds) {
        this.mList = data;
        this.layoutIds = layoutIds;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    /**
     * <p>在初始化的时候不能确定layoutId,才可以不提供,但是必须重写checkLayoutId方法</p>
     *
     * @param data    数据源
     * @param context 上下文
     * @deprecated
     */
    public BaseAdapter(List<T> data, Context context) {
        this.mList = data;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getViewCheckLayoutId(position);
        holder = holder.get(mContext, position, convertView, parent, layoutId);
        convert(holder, position, mList.get(position));
        return holder.getConvertView(layoutId);
    }

    private int getViewCheckLayoutId(int position) {
        int layoutId;
        if (layoutIds == null) {
            layoutId = checkLayoutId(position, mList.get(position));
        } else {
            if (layoutIds==null||layoutIds.length == 0) {
                throw new ArrayIndexOutOfBoundsException("not layoutId");
            }
            layoutId = layoutIds[checkLayoutIndex(position, mList.get(position))];
        }
        return layoutId;
    }

    /**
     * <p>实现具体控件的获取和赋值等业务</p>
     *
     * @param viewHolder viewHolder
     * @param position   position
     * @param t          数据源中,当前对应的bean
     */
    public abstract <BH extends BaseViewHolder> void convert(BH viewHolder, int position, T t);

    /**
     * <p>根据业务逻辑确定layoutId位置,使用在listView中有几种样式</p>
     *
     * @param position 所在位置
     * @param item     对应数据
     * @return 默认使用第一个, 返回下标, 从0开始
     */
    public int checkLayoutIndex(int position, T item) {
        return 0;
    }

    /**
     * <p>根据业务逻辑确定layoutId,只会在构造方法没有传入layoutId时生效</p>
     *
     * @param position 所在位置
     * @param item     对应数据
     * @return 默认为0, 返回Id
     */
    public int checkLayoutId(int position, T item) {
        return 0;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
