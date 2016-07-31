import java.util.ArrayList;
import java.util.List;

public class UpdatePostRequest {

    private String title;
    private Integer readCount;
    private String authorName;
    private List<Node__> nodes = new ArrayList<Node__>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public UpdatePostRequest() {
    }

    /**
     * 
     * @param nodes
     * @param authorName
     * @param title
     * @param readCount
     */
    public UpdatePostRequest(String title, Integer readCount, String authorName, List<Node__> nodes) {
        this.title = title;
        this.readCount = readCount;
        this.authorName = authorName;
        this.nodes = nodes;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The readCount
     */
    public Integer getReadCount() {
        return readCount;
    }

    /**
     * 
     * @param readCount
     *     The readCount
     */
    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    /**
     * 
     * @return
     *     The authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * 
     * @param authorName
     *     The authorName
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * 
     * @return
     *     The nodes
     */
    public List<Node__> getNodes() {
        return nodes;
    }

    /**
     * 
     * @param nodes
     *     The nodes
     */
    public void setNodes(List<Node__> nodes) {
        this.nodes = nodes;
    }

}
