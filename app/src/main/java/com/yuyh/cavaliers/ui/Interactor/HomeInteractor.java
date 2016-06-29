
package com.yuyh.cavaliers.ui.Interactor;

import android.content.Context;

import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.utils.NavigationEntity;

import java.util.List;

public interface HomeInteractor {

    List<BaseLazyFragment> getPagerFragments();

    List<NavigationEntity> getNavigationList(Context context);
}
