
package com.yuyh.sprintnba.ui.Interactor;

import android.content.Context;

import com.yuyh.sprintnba.base.BaseLazyFragment;
import com.yuyh.sprintnba.utils.NavigationEntity;

import java.util.List;

public interface HomeInteractor {

    List<BaseLazyFragment> getPagerFragments();

    List<NavigationEntity> getNavigationList(Context context);
}
