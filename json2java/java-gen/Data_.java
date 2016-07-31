import java.util.ArrayList;
import java.util.List;

public class Data_ {

    private String id;
    private String title;
    private Integer readCount;
    private String authorName;
    private Integer v;
    private List<Node___> nodes = new ArrayList<Node___>();
    private String time;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data_() {
    }

    /**
     * 
     * @param nodes
     * @param authorName
     * @param v
     * @param id
     * @param time
     * @param title
     * @param readCount
     */
    public Data_(String id, String title, Integer readCount, String authorName, Integer v, List<Node___> nodes, String time) {
        this.id = id;
        this.title = title;
        this.readCount = readCount;
        this.authorName = authorName;
        this.v = v;
        this.nodes = nodes;
        this.time = time;
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
     *     The v
     */
    public Integer getV() {
        return v;
    }

    /**
     * 
     * @param v
     *     The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }

    /**
     * 
     * @return
     *     The nodes
     */
    public List<Node___> getNodes() {
        return nodes;
    }

    /**
     * 
     * @param nodes
     *     The nodes
     */
    public void setNodes(List<Node___> nodes) {
        this.nodes = nodes;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

}
