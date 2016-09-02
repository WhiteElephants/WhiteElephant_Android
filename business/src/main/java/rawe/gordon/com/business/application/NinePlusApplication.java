package rawe.gordon.com.business.application;

import android.app.Application;

import java.io.File;

import rawe.gordon.com.business.db.DBManager;


/**
 * Created by gordon on 16/5/4.
 */
public class NinePlusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.getInstance().setContext(getApplicationContext());
        DBManager.getInstance().configure(getApplicationContext());
        initialize();
    }

    private void initialize() {
        File draftDir = new File(getExternalCacheDir().getAbsolutePath() + "/draft/");
        if (!draftDir.exists()) {
            draftDir.mkdirs();
        }
    }
}
