
package com.yuyh.cavaliers.Interactor;

import com.yuyh.cavaliers.http.bean.news.NewsDetail;
import com.yuyh.cavaliers.http.util.GetBeanCallback;

public interface NBANewsDetailInteractor {

    void getNewsDetail(String arcId, GetBeanCallback<NewsDetail> callback);
}
