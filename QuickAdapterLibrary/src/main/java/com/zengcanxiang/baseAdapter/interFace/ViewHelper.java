package com.zengcanxiang.baseAdapter.interFace;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.Adapter;

import com.zengcanxiang.baseAdapter.recyclerView.BaseViewHolder;

/**
 * adapter规范view操作接口
 *
 * @author zengcx
 */
public interface ViewHelper<VH> {

    interface RecyclerView<VH extends BaseViewHolder> {
        /**
         * 设置textView文本内容
         *
         * @param viewId viewId
         * @param value  文本内容
         * @return viewHolder
         */
        VH setText(int viewId, String value);

        /**
         * 设置imgView的图片,通过Id设置
         *
         * @param viewId   viewId
         * @param imgResId 图片Id
         * @return viewHolder
         */
        VH setImageResource(int viewId, int imgResId);

        /**
         * 设置背景颜色
         *
         * @param viewId viewId
         * @param color  颜色数值
         * @return viewHolder
         */
        VH setBackgroundColor(int viewId, int color);

        /**
         * 设置背景颜色
         *
         * @param viewId   viewId
         * @param colorRes 颜色Id
         * @return viewHolder
         */
        VH setBackgroundColorRes(int viewId, int colorRes);

        /**
         * 设置textView文本颜色
         *
         * @param viewId viewId
         * @param color  颜色数值
         * @return viewHolder
         */
        VH setTextColor(int viewId, int color);

        /**
         * 设置textView文本颜色
         *
         * @param viewId   viewId
         * @param colorRes 颜色Id
         * @return viewHolder
         */
        VH setTextColorRes(int viewId, int colorRes);

        /**
         * 设置img的Drawable
         *
         * @param viewId   viewId
         * @param drawable drawable
         * @return viewHolder
         */
        VH setImageDrawable(int viewId, Drawable drawable);

        /**
         * 设置img的Drawable
         *
         * @param viewId      viewId
         * @param drawableRes drawableId
         * @return viewHolder
         */
        VH setImageDrawableRes(int viewId, int drawableRes);

        /**
         * 设置img图片路径
         *
         * @param viewId viewId
         * @param imgUrl 图片路径
         * @return viewHolder
         */
        VH setImageUrl(int viewId, String imgUrl);

        /**
         * 设置img图片Bitmap
         *
         * @param viewId    viewId
         * @param imgBitmap imgBitmap
         * @return viewHolder
         */
        VH setImageBitmap(int viewId, Bitmap imgBitmap);

        /**
         * 设置控件是否隐藏
         *
         * @param viewId  viewId
         * @param visible visible
         * @return viewHolder
         */
        VH setVisible(int viewId, boolean visible);

        /**
         * 设置控件的tag
         *
         * @param viewId viewId
         * @param tag    tag
         * @return viewHolder
         */
        VH setTag(int viewId, Object tag);

        /**
         * 设置控件tag
         *
         * @param viewId viewId
         * @param key    tag的key
         * @param tag    tag
         * @return viewHolder
         */
        VH setTag(int viewId, int key, Object tag);

        /**
         * 设置Checkable控件的选择情况
         *
         * @param viewId  viewId
         * @param checked 选择
         * @return viewHolder
         */
        VH setChecked(int viewId, boolean checked);

        /**
         * 设置absListView的Adapter
         *
         * @param viewId  viewId
         * @param adapter adapter
         * @return viewHolder
         */
        VH setAdapter(int viewId, Adapter adapter);

        /**
         * 设置RecyclerView的Adapter
         *
         * @param viewId  viewId
         * @param adapter adapter
         * @return viewHolder
         */
        VH setAdapter(int viewId, android.support.v7.widget.RecyclerView.Adapter adapter);

        /**
         * 设置控件透明效果
         *
         * @param viewId viewId
         * @param value  透明值
         * @return viewHolder
         */
        VH setAlpha(int viewId, float value);

        /**
         * 设置textView正则
         *
         * @param viewId
         * @return viewHolder
         */
        VH linkify(int viewId);

        /**
         * 设置TextView字体
         *
         * @param viewId   viewId
         * @param typeface typeface
         * @return viewHolder
         */
        VH setTypeface(int viewId, Typeface typeface);

        /**
         * 设置多个TextView字体
         *
         * @param typeface typeface
         * @param viewIds  viewId组合
         * @return viewHolder
         */
        VH setTypeface(Typeface typeface, int... viewIds);

        /**
         * 设置ProgressBar控件进度
         *
         * @param viewId   viewId
         * @param progress progress
         * @return viewHolder
         */
        VH setProgress(int viewId, int progress);

        /**
         * 设置ProgressBar控件进度
         *
         * @param viewId   viewId
         * @param progress progress
         * @param max      max
         * @return viewHolder
         */
        VH setProgress(int viewId, int progress, int max);

        /**
         * 设置ProgressBar控件最大进度值
         *
         * @param viewId viewId
         * @param max    max
         * @return viewHolder
         */
        VH setMax(int viewId, int max);

        /**
         * 设置评分控件
         *
         * @param viewId viewId
         * @param rating 评分
         * @return viewHolder
         */
        VH setRating(int viewId, float rating);

        /**
         * 设置评分控件
         *
         * @param viewId viewId
         * @param rating 评分
         * @param max    最大
         * @return viewHolder
         */
        VH setRating(int viewId, float rating, int max);
    }

    interface AbsListView<VH extends com.zengcanxiang.baseAdapter.absListView.BaseViewHolder> {
        /**
         * 设置textView文本内容
         *
         * @param viewId viewId
         * @param value  文本内容
         * @return viewHolder viewHolder viewHolder
         */
        VH setText(int viewId, String value);

        /**
         * 设置imgView的图片,通过Id设置
         *
         * @param viewId   viewId
         * @param imgResId 图片Id
         * @return viewHolder viewHolder
         */
        VH setImageResource(int viewId, int imgResId);

        /**
         * 设置背景颜色
         *
         * @param viewId viewId
         * @param color  颜色数值
         * @return viewHolder viewHolder
         */
        VH setBackgroundColor(int viewId, int color);

        /**
         * 设置背景颜色
         *
         * @param viewId   viewId
         * @param colorRes 颜色Id
         * @return viewHolder
         */
        VH setBackgroundColorRes(int viewId, int colorRes);

        /**
         * 设置textView文本颜色
         *
         * @param viewId viewId
         * @param color  颜色数值
         * @return viewHolder
         */
        VH setTextColor(int viewId, int color);

        /**
         * 设置textView文本颜色
         *
         * @param viewId   viewId
         * @param colorRes 颜色Id
         * @return viewHolder
         */
        VH setTextColorRes(int viewId, int colorRes);

        /**
         * 设置img的Drawable
         *
         * @param viewId   viewId
         * @param drawable drawable
         * @return viewHolder
         */
        VH setImageDrawable(int viewId, Drawable drawable);

        /**
         * 设置img的Drawable
         *
         * @param viewId      viewId
         * @param drawableRes drawableId
         * @return viewHolder
         */
        VH setImageDrawableRes(int viewId, int drawableRes);


        /**
         * 设置img图片路径
         *
         * @param viewId viewId
         * @param imgUrl 图片路径
         * @return viewHolder
         */
        VH setImageUrl(int viewId, String imgUrl);

        /**
         * 设置img图片Bitmap
         *
         * @param viewId    viewId
         * @param imgBitmap imgBitmap
         * @return viewHolder
         */
        VH setImageBitmap(int viewId, Bitmap imgBitmap);

        /**
         * 设置控件是否隐藏
         *
         * @param viewId  viewId
         * @param visible visible
         * @return viewHolder
         */
        VH setVisible(int viewId, boolean visible);

        /**
         * 设置控件的tag
         *
         * @param viewId viewId
         * @param tag    tag
         * @return viewHolder
         */
        VH setTag(int viewId, Object tag);

        /**
         * 设置控件tag
         *
         * @param viewId viewId
         * @param key    tag的key
         * @param tag    tag
         * @return viewHolder
         */
        VH setTag(int viewId, int key, Object tag);

        /**
         * 设置Checkable控件的选择情况
         *
         * @param viewId  viewId
         * @param checked 选择
         * @return viewHolder
         */
        VH setChecked(int viewId, boolean checked);

        /**
         * 设置absListView的Adapter
         *
         * @param viewId  viewId
         * @param adapter adapter
         * @return viewHolder
         */
        VH setAdapter(int viewId, Adapter adapter);

        /**
         * 设置RecyclerView的Adapter
         *
         * @param viewId  viewId
         * @param adapter adapter
         * @return viewHolder
         */
        VH setAdapter(int viewId, android.support.v7.widget.RecyclerView.Adapter adapter);

        /**
         * 设置控件透明效果
         *
         * @param viewId viewId
         * @param value  透明值
         * @return viewHolder
         */
        VH setAlpha(int viewId, float value);

        /**
         * 设置textView正则
         *
         * @param viewId
         * @return viewHolder
         */
        VH linkify(int viewId);

        /**
         * 设置TextView字体
         *
         * @param viewId   viewId
         * @param typeface typeface
         * @return viewHolder
         */
        VH setTypeface(int viewId, Typeface typeface);

        /**
         * 设置多个TextView字体
         *
         * @param typeface typeface
         * @param viewIds  viewId组合
         * @return viewHolder
         */
        VH setTypeface(Typeface typeface, int... viewIds);

        /**
         * 设置ProgressBar控件进度
         *
         * @param viewId   viewId
         * @param progress progress
         * @return viewHolder
         */
        VH setProgress(int viewId, int progress);

        /**
         * 设置ProgressBar控件进度
         *
         * @param viewId   viewId
         * @param progress progress
         * @param max      max
         * @return viewHolder
         */
        VH setProgress(int viewId, int progress, int max);

        /**
         * 设置ProgressBar控件最大进度值
         *
         * @param viewId viewId
         * @param max    max
         * @return viewHolder
         */
        VH setMax(int viewId, int max);

        /**
         * 设置评分控件
         *
         * @param viewId viewId
         * @param rating 评分
         * @return viewHolder
         */
        VH setRating(int viewId, float rating);

        /**
         * 设置评分控件
         *
         * @param viewId viewId
         * @param rating 评分
         * @param max    最大
         * @return viewHolder
         */
        VH setRating(int viewId, float rating, int max);
    }
}
