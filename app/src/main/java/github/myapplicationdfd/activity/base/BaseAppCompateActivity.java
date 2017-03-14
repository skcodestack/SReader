package github.myapplicationdfd.activity.base;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import github.myapplicationdfd.utils.ActivityUtil;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.Helper.ViewHelperControl;
import github.myapplicationdfd.R;

/**
 * Author root
 * Date: 2016/8/26.
 */
public abstract class BaseAppCompateActivity extends AppCompatActivity{

    public Activity mActivity=null;

    private ViewHelperControl viewHelperControl=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        setTranslucentStatus(isApplyStatusBarTranslucency());

        mActivity=this;
        View rootView=initRootView();
        if(null!=getTagetView()) {
            viewHelperControl = new ViewHelperControl(getTagetView());
        }if(rootView!=null){
            setContentView(rootView);
        }else{
//            setContentView();
        }

        if (isApplyKitKatTranslucency()) {
           setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_color_primary));
        }
        initEvents();

    }

    /**
     * 初始化事件数据
     */
    protected abstract void initEvents();

    /**
     * 动态获取的视图
     * @return
     */
    protected abstract View getTagetView();

    /**
     * 获取根视图
     * @return
     */
    protected abstract View initRootView();

    /**
     *得到动画的mode
     * @return
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    /**
     * 是否执行  切换动画
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }

    public  boolean isApplyKitKatTranslucency() {
        return true;
    };

    public boolean isApplyStatusBarTranslucency() {
        return true;
    }

    /**
     * 切换动画
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    /**
     * show toash
     * @param msg
     */
    protected  void  showToast(String msg){
        if(!CommonUtils.isEmpty(msg)){
//            Snackbar.make(,msg,Snackbar.LENGTH_SHORT).show();
        }
    }


    /**
     * 没有返回值的跳转，
     * @param clazz
     * @param isFinish
     */
    public   void  readyTo(Class<?> clazz,boolean isFinish){
        ActivityUtil.readyGo(mActivity,clazz,null,isFinish);
    }

    /**
     * 有返回值的跳转，
     * @param clazz
     * @param isFinish
     */
    public   void  readyToForResult(Class<?> clazz,int requestcode,boolean isFinish){
        ActivityUtil.readyGoForResult(mActivity,clazz,null,requestcode,isFinish);
    }
    //显示的view
    /**
     * toggle show loading
     *
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == viewHelperControl) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            viewHelperControl.showLoading(msg);
        } else {
            viewHelperControl.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == viewHelperControl) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            viewHelperControl.showEmpty(msg, onClickListener);
        } else {
            viewHelperControl.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == viewHelperControl) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            viewHelperControl.showError(msg, onClickListener);
        } else {
            viewHelperControl.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle
     */
    protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == viewHelperControl) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            viewHelperControl.showNetWorkError(onClickListener);
        } else {
            viewHelperControl.restore();
        }
    }


    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }
}
