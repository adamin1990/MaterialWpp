package adamin90.com.wpp;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by LiTao on 2015-11-21-11:22.
 * Company: QD24so
 * Email: 14846869@qq.com
 * WebSite: http://lixiaopeng.top
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
