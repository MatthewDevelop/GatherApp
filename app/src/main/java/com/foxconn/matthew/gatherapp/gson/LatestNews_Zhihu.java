package com.foxconn.matthew.gatherapp.gson;

import java.util.List;

/**
 * Created by Matthew on 2017/11/20.
 */

public class LatestNews_Zhihu {

    public String date;

    public List<Story_Zhihu> stories;

    public List<TopStory_Zhihu> top_stories;

    @Override
    public String toString() {
        return "LatestNews_Zhihu{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_sotries=" + top_stories +
                '}';
    }
}
