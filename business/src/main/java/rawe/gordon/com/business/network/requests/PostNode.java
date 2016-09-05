package rawe.gordon.com.business.network.requests;

import java.util.ArrayList;

/**
 * Created by gordon on 9/5/16.
 */
public class PostNode {
    private int type;
    private ArrayList<String> mediaPaths;
    private String content;
    private String storagePath;
    private boolean title;

    public PostNode() {
    }

    public PostNode(String content, ArrayList<String> mediaPaths, String storagePath, boolean title, int type) {
        this.content = content;
        this.mediaPaths = mediaPaths;
        this.storagePath = storagePath;
        this.title = title;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getMediaPaths() {
        return mediaPaths;
    }

    public void setMediaPaths(ArrayList<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
