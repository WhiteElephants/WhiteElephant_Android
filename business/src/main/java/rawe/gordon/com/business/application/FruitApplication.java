package rawe.gordon.com.business.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import rawe.gordon.com.business.db.DBManager;


/**
 * Created by gordon on 16/5/4.
 */
public class FruitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.getInstance().setContext(getApplicationContext());
        DBManager.getInstance().configure(getApplicationContext());
        Fresco.initialize(getApplicationContext(), ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }
}
