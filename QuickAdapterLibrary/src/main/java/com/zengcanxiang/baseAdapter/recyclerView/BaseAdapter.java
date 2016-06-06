package com.zengcanxiang.baseAdapter.recyclerView;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * <p>万能适配Adapter,减少赘于代码和加快开发流程</p>
 *
 * @author zengcx
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BH> {
    protected List<T> mList;
    protected Context mContext;
    protected LayoutInflater mLInflater;
    protected int[] mLayoutId;
    private SparseArray<View> mConvertViews = new SparseArray<View>();

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public BaseAdapter(List<T> data, Context context, int... layoutId) {
        this.mList = data;
        this.mLayoutId = layoutId;
        this.mContext = context;
        this.mLInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return checkLayoutIndex(mList.get(position), position);
    }

    @Override
    public BH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > mLayoutId.length) {
            throw new ArrayIndexOutOfBoundsException("checkLayoutIndex > LayoutId.length");
        }
        if (mLayoutId.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = mLayoutId[viewType];
        View view = inflateItemView(layoutId, parent);
        BaseViewHolder viewHolder = (BaseViewHolder) view.getTag();
        if (viewHolder == null || viewHolder.getLayoutId() != layoutId) {
            viewHolder = new BaseViewHolder(mContext, layoutId, view);
            return viewHolder;
        }
        return viewHolder;
    }

    /**
     * 解析布局资源
     *
     * @param layoutId
     * @param viewGroup
     * @return
     */
    protected View inflateItemView(int layoutId, ViewGroup viewGroup) {
        View convertView = mConvertViews.get(layoutId);
        if (convertView == null) {
            convertView = mLInflater.inflate(layoutId,
                    viewGroup, false);
        }
        return convertView;
    }

    @Override
    public void onBindViewHolder(BH holder, int position) {
        final T item = mList.get(position);
        // 绑定数据
        onBindData(holder, position, item);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 绑定数据到Item View上
     *
     * @param viewHolder
     * @param position   数据的位置
     * @param item       数据项
     */
    protected abstract void onBindData(BH viewHolder, int position, T item);

    public int checkLayoutIndex(T item, int position) {
        return 0;
    }

}
