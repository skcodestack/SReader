package github.myapplicationdfd.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Author root
 * Date: 2016/8/26.
 */
public class CommonUtils {

    public static  boolean isEmpty(String msg){
        return  TextUtils.isEmpty(msg);
    }


    public static  Context getContext(){
        return  null;
    }
    public  static  String  getString (int  id){
        return  getContext().getResources().getString(id);
    }
}
