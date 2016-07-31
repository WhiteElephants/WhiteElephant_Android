package com.gordon.rawe.business.pojo;

public class Node_ {

    private String imageDescription;
    private String imageUrl;
    private String text;
    private String id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Node_() {
    }

    /**
     * 
     * @param imageDescription
     * @param imageUrl
     * @param text
     * @param id
     */
    public Node_(String imageDescription, String imageUrl, String text, String id) {
        this.imageDescription = imageDescription;
        this.imageUrl = imageUrl;
        this.text = text;
        this.id = id;
    }

    /**
     * 
     * @return
     *     The imageDescription
     */
    public String getImageDescription() {
        return imageDescription;
    }

    /**
     * 
     * @param imageDescription
     *     The imageDescription
     */
    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    /**
     * 
     * @return
     *     The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 
     * @param imageUrl
     *     The imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(String id) {
        this.id = id;
    }

}
