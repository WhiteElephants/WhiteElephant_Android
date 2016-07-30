package rawe.gordon.com.business.network.responses.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gordon on 16/5/10.
 */
public class CommentModel implements Serializable {
    private String content;

    private String id;

    private String time;

    private UserModel user;

    private List<String> pictures;

    public CommentModel() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }
}
