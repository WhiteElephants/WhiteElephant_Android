package rawe.gordon.com.business.network.responses.pojo;

/**
 * Created by gordon on 5/6/16.
 */
public class SlideModel {
    private String imageUrl;
    private String redirectUrl;

    public SlideModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SlideModel(String imageUrl, String redirectUrl) {
        this.imageUrl = imageUrl;
        this.redirectUrl = redirectUrl;
    }

    public SlideModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
