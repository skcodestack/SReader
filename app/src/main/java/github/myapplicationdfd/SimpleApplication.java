package github.myapplicationdfd;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.x;

import github.myapplicationdfd.utils.ImageLoaderHelper;

/**
 * Author root
 * Date: 2016/8/26.
 */
public class SimpleApplication extends Application {
//    public static  Context getContext1(){
//        return  getContext();
//    }


    @Override
        public void onCreate() {
            super.onCreate();
            x.Ext.init(this);
            x.Ext.setDebug(true);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(CommonConifg.IMAGE_LOADER_CACHE_PATH));
    }
}
