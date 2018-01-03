package com.foxconn.matthew.gatherapp.gson;

/**
 * Created by Matthew on 2017/11/20.
 */

public class TopStory_Zhihu {

    public String image;

    public int type;

    public int id;

    public int ga_prefix;

    public String title;

    @Override
    public String toString() {
        return "TopStory_Zhihu{" +
                "image='" + image + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", ga_prefix=" + ga_prefix +
                ", title='" + title + '\'' +
                '}';
    }
}
