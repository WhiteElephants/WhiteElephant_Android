package rawe.gordon.com.business.application;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.AppCompatDrawableManager;
import android.view.Display;

/**
 * Created by gordon on 16/5/7.
 */
public class SharedParameter {

    private static SharedParameter ourInstance = new SharedParameter();

    public static SharedParameter getInstance() {
        return ourInstance;
    }

    private SharedParameter() {
    }

    private int screenWidth = -1;
    private int screenHeight = -1;
    private AppCompatDrawableManager drawableManager;

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public AppCompatDrawableManager getDrawableManager() {
        return drawableManager;
    }

    public void setDrawableManager(AppCompatDrawableManager drawableManager) {
        this.drawableManager = drawableManager;
    }

    public void generateScreenParameters(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        setScreenWidth(size.x);
        setScreenHeight(size.y);
        drawableManager = AppCompatDrawableManager.get();
    }
}
