package com.gordon.rawe.business.pojo;

public class CreatePost {

    private Boolean status;
    private String message;
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreatePost() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public CreatePost(Boolean status, String message, Data data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

}
