
package com.yuyh.cavaliers.Interactor;

import android.content.Context;

import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.bean.NavigationEntity;

import java.util.List;

public interface HomeInteractor {

    List<BaseLazyFragment> getPagerFragments();

    List<NavigationEntity> getNavigationList(Context context);
}
