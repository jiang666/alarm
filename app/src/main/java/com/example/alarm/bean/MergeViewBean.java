package com.example.alarm.bean;

import java.util.List;

public class MergeViewBean {
    String id;
    String name;
    String type;
    int speakerNum;
    boolean isGroup;
    int color;
    List<MergeViewBean> mergeViewBeans;

    public MergeViewBean() {
    }

    public MergeViewBean(String id, String name, String type, int speakerNum, boolean isGroup, int color, List<MergeViewBean> mergeViewBeans) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.speakerNum = speakerNum;
        this.isGroup = isGroup;
        this.color = color;
        this.mergeViewBeans = mergeViewBeans;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeakerNum() {
        return speakerNum;
    }

    public void setSpeakerNum(int speakerNum) {
        this.speakerNum = speakerNum;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<MergeViewBean> getMergeViewBeans() {
        return mergeViewBeans;
    }

    public void setMergeViewBeans(List<MergeViewBean> mergeViewBeans) {
        this.mergeViewBeans = mergeViewBeans;
    }
}
