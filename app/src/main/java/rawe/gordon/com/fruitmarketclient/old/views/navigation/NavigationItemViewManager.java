package rawe.gordon.com.fruitmarketclient.old.views.navigation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gordon on 16/5/5.
 */
public class NavigationItemViewManager {
    private static NavigationItemViewManager ourInstance = new NavigationItemViewManager();

    public static NavigationItemViewManager getInstance() {
        return ourInstance;
    }

    private NavigationItemViewManager() {
    }

    private List<ExpandableItemView> expandableItemViews = new ArrayList<>();

    public void addView(ExpandableItemView expandableItemView) {
        expandableItemViews.add(expandableItemView);
    }

    public void performStatusChange() {
        for (ExpandableItemView ex : expandableItemViews) {
            if (ex.state) {
                ex.toggleViews();
            }
        }
    }
}
