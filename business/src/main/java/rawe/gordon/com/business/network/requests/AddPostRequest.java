package rawe.gordon.com.business.network.requests;

import com.gordon.rawe.business.models.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gordon on 9/5/16.
 */
public class AddPostRequest {
    public String author;
    public String title;
    public ArrayList<PostNode> nodes;
    public int readCount;
    public int wordCount;
    public String category;

    public AddPostRequest() {
    }

    public AddPostRequest(String author, String category, ArrayList<PostNode> nodes, int readCount, String title, int wordCount) {
        this.author = author;
        this.category = category;
        this.nodes = nodes;
        this.readCount = readCount;
        this.title = title;
        this.wordCount = wordCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<PostNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<PostNode> nodes) {
        this.nodes = nodes;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public static AddPostRequest createSamplePost() {
        AddPostRequest request = new AddPostRequest();
        request.setAuthor("gordon");
        request.setCategory("science");
        request.setReadCount(1000);
        request.setWordCount(2000);
        request.setTitle("this is a title");
        ArrayList<PostNode> nodes = new ArrayList<>();
        nodes.add(new PostNode());
        request.setNodes(nodes);
        return request;
    }
}
