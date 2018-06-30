package nata.com;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.MobileAds;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import nata.com.nata.R;
import nata.com.utility.Utility;

/**
 * Created by Shankar on 11/1/2016.
 */
public class NataApplication extends MultiDexApplication {

    private static NataApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initImageLoader(getApplicationContext());
        MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.banner_ad_unit_id));
        //MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.interstitial_ad_unit_id));
        MobileAds.initialize(getApplicationContext(), Utility.getResourcesString(this, R.string.native_ad_unit_id));

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
    }

    public static synchronized NataApplication getInstance() {
        return mInstance;
    }
}
