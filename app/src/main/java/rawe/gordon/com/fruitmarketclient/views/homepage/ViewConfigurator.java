package rawe.gordon.com.fruitmarketclient.views.homepage;

/**
 * Created by gordon on 16/5/4.
 */
public class ViewConfigurator {

    public interface LeftMenuListener {
        void onLeftButtonClicked();

        void onRightButtonClicked();

        void onTextClicked(String keyword);
    }
}
