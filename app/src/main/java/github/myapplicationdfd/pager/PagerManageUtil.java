package github.myapplicationdfd.pager;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import github.myapplicationdfd.utils.CommonUtils;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class PagerManageUtil {
    private HashMap<String ,SoftReference<BasePager>> map=null;
    public  PagerManageUtil(){
        map=new HashMap<>();
    }

    public  void  put(String  key,BasePager pager){
        SoftReference<BasePager> value=new SoftReference<BasePager>(pager);

        if(map!=null){
            map.put(key,value);
        }
    }

    public  BasePager get(String key){

        if(CommonUtils.isEmpty(key)){
            return  null;
        }
        if(map==null){
            return  null;
        }

        SoftReference<BasePager> softReference = map.get(key);
        if(softReference!=null){
            BasePager pager = softReference.get();
            if(pager!=null){
                return  pager;
            }
        }
        return  null;
    }

}
