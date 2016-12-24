package com.yuyh.sprintnba.ui.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;
import com.yuyh.sprintnba.R;
import com.yuyh.sprintnba.http.bean.video.VideoLiveInfo;
import com.yuyh.sprintnba.utils.FrescoUtils;
import com.yuyh.sprintnba.utils.ItemAnimHelper;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/12/23.
 */
public class VideoLiveAdapter extends EasyLVAdapter<VideoLiveInfo> {

    private ItemAnimHelper helper = new ItemAnimHelper();

    public VideoLiveAdapter(Context context, List<VideoLiveInfo> list) {
        super(context, list, R.layout.item_live_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, VideoLiveInfo info) {

        holder.setText(R.id.tvLeftTeam, info.leftName)
                .setText(R.id.tvRightTeam, info.rightName)
                .setText(R.id.tvLiveTime, info.time)
                .setText(R.id.tvLiveType, info.type);

        SimpleDraweeView ivLeft = holder.getView(R.id.ivLeftTeam);
        ivLeft.setController(FrescoUtils.getController(info.leftImg, ivLeft));

        SimpleDraweeView ivRight = holder.getView(R.id.ivRightTeam);
        ivRight.setController(FrescoUtils.getController(info.rightImg, ivRight));

        helper.showItemAnim(holder.getConvertView(), position);
    }
}
