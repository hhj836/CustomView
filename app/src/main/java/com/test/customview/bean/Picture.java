package com.test.customview.bean;

/**
 * Created by hhj on 2018/3/19.
 */

public class Picture {
    int width;
    int height;
    String src;

    public Picture(int width, int height, String src) {
        this.width = width;
        this.height = height;
        this.src = src;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
