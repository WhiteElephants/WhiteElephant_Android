package com.gordon.rawe.business.pojo;

public class UpdatePost {

    private Boolean status;
    private String message;
    private Data_ data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdatePost() {
    }

    /**
     * 
     * @param data
     * @param message
     * @param status
     */
    public UpdatePost(Boolean status, String message, Data_ data) {
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
    public Data_ getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data_ data) {
        this.data = data;
    }

}
