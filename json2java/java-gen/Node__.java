
public class Node__ {

    private String text;
    private String imageUrl;
    private String imageDescription;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Node__() {
    }

    /**
     * 
     * @param imageUrl
     * @param imageDescription
     * @param text
     */
    public Node__(String text, String imageUrl, String imageDescription) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.imageDescription = imageDescription;
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

}
