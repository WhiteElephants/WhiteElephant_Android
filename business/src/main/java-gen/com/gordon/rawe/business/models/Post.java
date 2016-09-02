package com.gordon.rawe.business.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "POST".
 */
public class Post {

    private Long id;
    private String uuid;
    private String data;
    private String createTime;
    private String postName;
    private String thumbPath;
    private String tag;
    private String category;

    public Post() {
    }

    public Post(Long id) {
        this.id = id;
    }

    public Post(Long id, String uuid, String data, String createTime, String postName, String thumbPath, String tag, String category) {
        this.id = id;
        this.uuid = uuid;
        this.data = data;
        this.createTime = createTime;
        this.postName = postName;
        this.thumbPath = thumbPath;
        this.tag = tag;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
