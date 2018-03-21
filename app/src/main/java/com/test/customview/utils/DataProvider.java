package com.test.customview.utils;

import com.test.customview.bean.Picture;

import java.util.ArrayList;

/**
 * Created by hhj on 2018/3/19.
 */

public class DataProvider {
    static final Picture[] VIRTUAL_PICTURE = {
            new Picture(566,800,"http://o84n5syhk.bkt.clouddn.com/57154327_p0.png"),
            new Picture(2126,1181,"http://o84n5syhk.bkt.clouddn.com/57180221_p0.jpg"),
            new Picture(1142,800,"http://o84n5syhk.bkt.clouddn.com/57174070_p0.jpg"),
            new Picture(550,778,"http://o84n5syhk.bkt.clouddn.com/57166531_p0.jpg"),
            new Picture(1085,755,"http://o84n5syhk.bkt.clouddn.com/57151022_p0.jpg"),
            new Picture(656,550,"http://o84n5syhk.bkt.clouddn.com/57172236_p0.jpg"),
            new Picture(1920,938,"http://o84n5syhk.bkt.clouddn.com/57174564_p0.jpg"),
            new Picture(1024,683,"http://o84n5syhk.bkt.clouddn.com/57156832_p0.jpg"),
            new Picture(723,1000,"http://o84n5syhk.bkt.clouddn.com/57151474_p0.png"),
            new Picture(2000,1667,"http://o84n5syhk.bkt.clouddn.com/57156623_p0.png"),
    };
    public static ArrayList<Picture> getPictures(int page){
        ArrayList<Picture> arrayList = new ArrayList<>();
        for (int i = 0; i < VIRTUAL_PICTURE.length; i++) {
            arrayList.add(VIRTUAL_PICTURE[i]);
        }
        return arrayList;
    }

}
