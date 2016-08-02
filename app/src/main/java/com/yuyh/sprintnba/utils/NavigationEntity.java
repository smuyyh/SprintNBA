
package com.yuyh.sprintnba.utils;

import java.io.Serializable;

public class NavigationEntity implements Serializable{

    private int iconResId;
    private String name;

    public NavigationEntity(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
