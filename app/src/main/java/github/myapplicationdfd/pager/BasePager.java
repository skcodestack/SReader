package github.myapplicationdfd.pager;

import android.app.Activity;
import android.view.View;

import github.myapplicationdfd.Helper.ViewHelperControl;

/**
 * Author root
 * Date: 2016/8/30.
 *
 *
 *init-->initView---->getTagerView---->initData
 * 手动--》initEvent
 */
public abstract class BasePager {
    protected Activity mActivity;
    private  View  rootView;
    private View tagerView;
    private ViewHelperControl viewHelperControl;
    private Object[] objs;

    public Object[] getParams(){
        return objs;
    }
    public Object getParam(int pos){
        Object tem=null;
        try{
            tem=objs[pos];
        }catch (Exception ex){
            tem=new Object();
        }
        return tem;
    }
    public BasePager(Activity mActivity){
            this.mActivity=mActivity;
            init();
        }
    public BasePager(Activity mActivity,Object... obj){
        this.mActivity=mActivity;
//        this.objs=obj;
        init();
    }
    private void init() {
        this.rootView = initView();

        viewHelperControl = new ViewHelperControl(getTagerView());
        onCreatedView();
    }

    protected abstract void onCreatedView();

    public  View  getView(){
        return  rootView;
    }

    public  abstract View initView() ;

    public  abstract void  initData();

    public  abstract View getTagerView() ;


    protected  void  showLoading(String msg){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.showLoading(msg);
    }
    protected  void  showError(String msg,View.OnClickListener callback){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.showError(msg,callback);
    }
    protected  void  showSuccessView(){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.restore();
    }
    protected  void  showEmpty(String msg,View.OnClickListener callback){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.showEmpty(msg,callback);
    }
    protected  void  showEmpty(View.OnClickListener callback){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.showNetWorkError(callback);
    }

}
