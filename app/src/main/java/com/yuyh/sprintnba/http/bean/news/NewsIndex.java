package com.yuyh.sprintnba.http.bean.news;

import com.yuyh.sprintnba.http.bean.base.Base;

import java.io.Serializable;
import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/3.
 */
public class NewsIndex extends Base {


    /**
     * type : news
     * id : 20160603042788
     * column : banner
     * needUpdate : 0
     */

    public List<IndexBean> data;

    public static class IndexBean implements Serializable {
        private String type;
        private String id;
        private String column;
        private String needUpdate;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getNeedUpdate() {
            return needUpdate;
        }

        public void setNeedUpdate(String needUpdate) {
            this.needUpdate = needUpdate;
        }
    }
}
