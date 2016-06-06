package com.yuyh.library.view.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ListView或GridView等的适配器视图缓存公共类，在adapter的getView()中使用
 * public View getView(int position, View convertView, ViewGroup parent){
 *      //实例化一个viewHolder
 *      ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.item_single_str, position);
 *      //通过getView获取控件
 *      TextView tv = viewHolder.getView(R.id.id_tv_title);
 *      //使用
 *      tv.setText(mDatas.get(position));
 *      return viewHolder.getConvertView();
 * }
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class ViewHolder {
    private final SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);

    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
