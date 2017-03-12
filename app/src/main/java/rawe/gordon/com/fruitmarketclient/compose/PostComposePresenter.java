package rawe.gordon.com.fruitmarketclient.compose;

/**
 * Created by gordon on 3/12/17.
 */

public class PostComposePresenter implements PostComposeScenario.Presenter {

    PostComposeScenario.View view;
    PostCompostModel model;

    public PostComposePresenter(PostComposeScenario.View view, PostCompostModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        view.findViews();
        view.bindActions();
        view.displayData(model.mediaInfos);
    }
}
