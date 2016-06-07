
package com.yuyh.cavaliers.Interactor;

import android.content.Context;

import com.yuyh.cavaliers.base.BaseLazyFragment;
import com.yuyh.cavaliers.bean.NavigationEntity;

import java.util.List;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/12.
 * Description:
 */
public interface HomeInteractor {

    List<BaseLazyFragment> getPagerFragments();

    List<NavigationEntity> getNavigationListData(Context context);
}
