package github.myapplicationdfd.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import org.xutils.common.Callback;

import java.util.ArrayList;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.NewsTabBean;
import github.myapplicationdfd.Helper.ViewHelperControl;
import github.myapplicationdfd.R;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Json2bean;
import github.myapplicationdfd.view.XViewPager;

/**
 * Author root
 * Date: 2016/8/29.
 *
 * 什么周期：initviewpager-->initdate
 */
public abstract class BaseFragment extends Fragment {
    protected  View rootView;
    protected Activity mActivity;
    private ViewHelperControl viewHelperControl;
    /**
     * 附加
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }
    /**
     * 创建view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=initView();
        if(getTarView()!=null) {
            viewHelperControl = new ViewHelperControl(getTarView());
        }
        return rootView;
    }
    /**
     * 创建完成
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    public  View  getView(){
        return  rootView;
    }
    /**
     * 初始化数据
     */
    protected abstract void initDate();
    /**
     * 返回view
     * @return
     */
    protected abstract View initView();

    /**
     * 目标视图
     * @return
     */
    protected abstract View getTarView();




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
    protected  void  showNetWorkError(View.OnClickListener callback){
        if(viewHelperControl==null){
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        viewHelperControl.showNetWorkError(callback);
    }
}
