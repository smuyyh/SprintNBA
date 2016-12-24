package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.graphics.Color;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/28.
 */
public class ReportAdapter extends EasyLVAdapter<String> {

    private int selectedPosition = -1;// 选中的位置

    public ReportAdapter(List<String> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public void convert(EasyLVHolder viewHolder, int position, String item) {
        viewHolder.setText(R.id.tvType, item);
        if (selectedPosition == position) {
            viewHolder.getConvertView().setSelected(true);
            viewHolder.getConvertView().setPressed(true);
            viewHolder.getConvertView().setBackgroundColor(Color.parseColor("#DDDDDD"));
            viewHolder.setVisible(R.id.ivCheck, true);
        } else {
            viewHolder.getConvertView().setSelected(false);
            viewHolder.getConvertView().setPressed(false);
            viewHolder.getConvertView().setBackgroundColor(Color.TRANSPARENT);
            viewHolder.setVisible(R.id.ivCheck, false);
        }
    }
}
