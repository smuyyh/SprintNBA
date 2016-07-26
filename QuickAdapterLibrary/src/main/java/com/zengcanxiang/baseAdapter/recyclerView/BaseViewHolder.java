package com.zengcanxiang.baseAdapter.recyclerView;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

/**
 * <p>万能适配Holder,减少赘于代码和加快开发流程</p>
 *
 * @author zengcx
 */
public class BaseViewHolder extends BH {

    private SparseArray<View> mViews = new SparseArray<>();
    private View mConvertView;
    private int mLayoutId;
    protected Context mContext;

    public BaseViewHolder(Context context, int layoutId, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mLayoutId = layoutId;
        mConvertView = itemView;
        mConvertView.setTag(this);
    }

    @SuppressWarnings("unchecked")
    public <R extends View> R getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (R) view;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public View getItemView() {
        return mConvertView;
    }
}
