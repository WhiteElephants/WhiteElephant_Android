package rawe.gordon.com.business.application;

import rawe.gordon.com.business.utils.PreferencesHelper;

/**
 * Created by gordon on 16/5/13.
 */
public class SystemConfig {
    private static SystemConfig ourInstance = new SystemConfig();

    public static SystemConfig getInstance() {
        return ourInstance;
    }

    private SystemConfig() {
        acceptSystemNotification = isAcceptSystemNotification();
        acceptActivitiesRecommendations = isAcceptActivitiesRecommendations();
    }

    private boolean acceptSystemNotification;
    private boolean acceptActivitiesRecommendations;

    public static SystemConfig getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(SystemConfig ourInstance) {
        SystemConfig.ourInstance = ourInstance;
    }

    public boolean isAcceptSystemNotification() {
        return PreferencesHelper.getInstance().getBoolean(SharedKeys.KEY_SYSTEM_NOTIFICATION, true);
    }

    public void setAcceptSystemNotification(boolean acceptSystemNotification) {
        this.acceptSystemNotification = acceptSystemNotification;
        PreferencesHelper.getInstance().putBoolean(SharedKeys.KEY_SYSTEM_NOTIFICATION, acceptSystemNotification);
    }

    public boolean isAcceptActivitiesRecommendations() {
        return PreferencesHelper.getInstance().getBoolean(SharedKeys.KEY_ACTIVITY_RECOMMENDATION, true);
    }

    public void setAcceptActivitiesRecommendations(boolean acceptActivitiesRecommendations) {
        this.acceptActivitiesRecommendations = acceptActivitiesRecommendations;
        PreferencesHelper.getInstance().putBoolean(SharedKeys.KEY_ACTIVITY_RECOMMENDATION, acceptActivitiesRecommendations);
    }
}
