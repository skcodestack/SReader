package github.myapplicationdfd.fragment;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import github.myapplicationdfd.pager.NewsTabPager;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class FragmentManageUtil {

    private HashMap<String ,SoftReference<BaseFragment>> map=null;
    public  FragmentManageUtil(){
        map=new HashMap<>();
    }
    public  void  set(String key,BaseFragment fragment){
        map.put(key,new SoftReference<BaseFragment>(fragment));
    }
    public  BaseFragment  get(String  key){
        if(map==null ){
            return null;
        }
        SoftReference<BaseFragment> softReference = map.get(key);
        if(softReference==null){
            return null;
        }
        BaseFragment fragment = softReference.get();
        if(fragment!=null){
            return  fragment;
        }
        return  null;
    }

}
