package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.match.MatchStat;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/4.
 */
public class MatchStatisticsAdapter extends EasyLVAdapter<MatchStat.TeamStats> {

    private Context mContext;
    private int colorPrimary;

    public MatchStatisticsAdapter(List<MatchStat.TeamStats> mList, Context context, int... layoutIds) {
        super(context, mList, layoutIds);
        mContext = context;
        colorPrimary = ContextCompat.getColor(context, R.color.colorPrimary);
    }

    @Override
    public void convert(EasyLVHolder viewHolder, final int position, final MatchStat.TeamStats item) {
        viewHolder.setText(R.id.tvLeftVal, item.leftVal + "")
                .setText(R.id.tvRightVal, item.rightVal + "")
                .setText(R.id.tvStatisticsName, item.text);

        final LinearLayout llLeftProgress = viewHolder.getView(R.id.llLeftProgress);
        final LinearLayout llRightProgress = viewHolder.getView(R.id.llRightProgress);
        int sum = item.leftVal + item.rightVal;
        final float left = sum <= 0 ? 0 : (float) item.leftVal / (float) sum;
        final float right = sum <= 0 ? 0 : (float) item.rightVal / (float) sum;

        ViewTreeObserver vto = llRightProgress.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 务必移除监听，会多次调用
                if (Build.VERSION.SDK_INT < 16) {
                    llRightProgress.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    llRightProgress.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                View leftLine = new View(mContext);
                leftLine.setBackgroundColor(colorPrimary);
                int leftWidth = llLeftProgress.getWidth();
                int newWidth = (int) (leftWidth * left);
                //LogUtils.e("newwidth = " + newWidth + " leftWidth=" + leftWidth + " left=" + left);
                LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(newWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                leftLine.setLayoutParams(leftParams);
                llLeftProgress.setGravity(Gravity.RIGHT);
                llLeftProgress.addView(leftLine);

                View rightLine = new View(mContext);
                rightLine.setBackgroundColor(colorPrimary);
                int rightWidth = llRightProgress.getWidth();
                LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams((int) (rightWidth * right), ViewGroup.LayoutParams.MATCH_PARENT);
                rightLine.setLayoutParams(rightParams);
                llRightProgress.setGravity(Gravity.LEFT);
                llRightProgress.addView(rightLine);
                if (position == 0) { // 没明白为什么，第一条数据的param总是不生效，移除重新添加就可以...
                    llLeftProgress.removeAllViews();
                    llLeftProgress.addView(leftLine);
                    llRightProgress.removeAllViews();
                    llRightProgress.addView(rightLine);
                }

                if (item.leftVal > item.rightVal) {
                    rightLine.setBackgroundColor(Color.GRAY);
                } else if (item.leftVal < item.rightVal) {
                    leftLine.setBackgroundColor(Color.GRAY);
                }
            }
        });
    }
}
