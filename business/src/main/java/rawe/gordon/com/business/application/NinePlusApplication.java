package rawe.gordon.com.business.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LRULimitedMemoryCache((int) (Runtime.getRuntime().maxMemory() / 10)))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}
