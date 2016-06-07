package com.yuyh.cavaliers.view;

import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.bean.NavigationEntity;

import java.util.List;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/9.
 * Description:
 */
public interface HomeView {

    void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList);

}
