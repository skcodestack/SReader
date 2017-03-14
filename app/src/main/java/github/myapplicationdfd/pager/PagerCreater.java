package github.myapplicationdfd.pager;

import android.app.Activity;

/**
 * Author : root
 * QQ     : 1562363326
 * Date   : 2017/3/8.
 */

public class PagerCreater {

    private static  volatile  PagerCreater instance=null;

    private PagerCreater(){}

    public static PagerCreater getInstance(){
        if(instance==null){
            synchronized (PagerCreater.class){
                if(instance==null){
                    instance=new PagerCreater();
                }
            }
        }
        return instance;
    }

    /**
     * 根据不同的code生产不同的pager
     * @param mActivity
     * @param code
     */
    public  BasePager  createNewsPager(Activity mActivity,String code){

        return new NewsTabPager(mActivity,code);
    }
    public  BasePager  createPicturePager(Activity mActivity,String code){

        return new PictureTabPager(mActivity,code);
    }

}
