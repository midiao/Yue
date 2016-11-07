package com.superjunior.yue.model.zhihudaily;

import java.util.List;

/**
 * Created by cb8695 on 2016/11/7.
 */

public class ZhiHuDailyResult {

    private String date;

    private List<StoryBean> stories;

    private List<TopStoryBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public List<TopStoryBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoryBean> top_stories) {
        this.top_stories = top_stories;
    }
}
