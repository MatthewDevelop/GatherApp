package com.foxconn.matthew.gatherapp.gson;

/**
 * Created by Matthew on 2017/11/20.
 */

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 *  {
 *  "images": [
 *  "https://pic2.zhimg.com/v2-a61d58fd0748d55c35b2870b8e93dfe5.jpg"
 *  ],
 *  "type": 0,
 *  "id": 9657030,
 *  "ga_prefix": "112009",
 *  "title": "什么时候，单身成了一种需要「被关怀」的事？"
 *  }
 */

public class Story {

    public String[] images;

    public int type;

    public int id;

    public int ga_prefix;

    public String title;

    @Override
    public String toString() {
        return "Story{" +
                "images=" + Arrays.toString(images) +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix=" + ga_prefix +
                ", title='" + title + '\'' +
                '}';
    }
}
