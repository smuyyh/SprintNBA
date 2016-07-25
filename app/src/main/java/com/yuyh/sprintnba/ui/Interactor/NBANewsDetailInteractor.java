
package com.yuyh.sprintnba.ui.Interactor;

import com.yuyh.sprintnba.http.bean.news.NewsDetail;
import com.yuyh.sprintnba.http.api.RequestCallback;

public interface NBANewsDetailInteractor {

    void getNewsDetail(String arcId, RequestCallback<NewsDetail> callback);
}
