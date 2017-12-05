package com.foxconn.matthew.gatherapp.gson;

import java.util.List;

/**
 * Created by Matthew on 2017/11/20.
 */

public class LatestNews {

    public String date;

    public List<Story> stories;

    public List<TopStory> top_stories;

    @Override
    public String toString() {
        return "LatestNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_sotries=" + top_stories +
                '}';
    }
}
