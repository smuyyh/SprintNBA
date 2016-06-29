package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.utils.NavigationEntity;

import java.util.List;

public interface HomeView {

    void initializeViews(List<BaseLazyFragment> fragments, List<NavigationEntity> navigationList);

}
