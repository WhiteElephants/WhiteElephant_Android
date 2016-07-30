package rawe.gordon.com.business.network.responses;

import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.SlideModel;

/**
 * Created by gordon on 16/5/5.
 */
public class SlideResponse extends BaseResponse {
    private List<SlideModel> slides;

    public List<SlideModel> getSlides() {
        return slides;
    }

    public void setSlides(List<SlideModel> slides) {
        this.slides = slides;
    }
}
