package github.myapplicationdfd.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Author root
 * Date: 2016/8/26.
 */
public class ActivityUtil {

    public static  void  readyGo(Activity mActivity, Class<?> clazz,Bundle bundle,boolean isFinish){
        Intent  intent =new Intent(mActivity,clazz);
        if(bundle!=null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
        if(isFinish){mActivity.finish();}
    }
    public static void  readyGoForResult(Activity mActivity, Class<?> clazz,Bundle bundle,int requestcode,boolean isFinish){
        Intent  intent =new Intent(mActivity,clazz);
        if(bundle!=null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent,requestcode);
        if(isFinish){mActivity.finish();}
    }


}
