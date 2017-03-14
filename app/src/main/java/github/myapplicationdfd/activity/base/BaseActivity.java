package github.myapplicationdfd.activity.base;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import github.myapplicationdfd.R;
import github.myapplicationdfd.fragment.NewsFragment;

/**
 * Author root
 * Date: 2016/8/29.
 */
public abstract class BaseActivity  extends BaseAppCompateActivity{

    private View view;
    public  FrameLayout fl_left;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;


    protected void setToolBarTile(String title){
        if(toolbar!=null) {
            toolbar.setTitle(title);
        }
    }

    protected  void  CloseLeft(){
        if(drawer_layout!=null) {
            drawer_layout.closeDrawer(fl_left);
        }
    }
    @Override
    protected void initEvents() {
        //toolbar
        toolbar = (Toolbar) view.findViewById(R.id.common_toolbar);
        toolbar.setTitle("新闻快递");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //drawer
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer_layout, toolbar,R.string.open,R.string.close);

        toggle.syncState();
        drawer_layout.setDrawerListener(toggle);

        FrameLayout fl_content = (FrameLayout) getTagetView();





    }

    @Override
    protected View getTagetView() {
        View fl_content = view.findViewById(R.id.fl_content);
        return fl_content;
    }

    @Override
    protected View initRootView() {
        view = mActivity.getLayoutInflater().inflate(R.layout.activity_main,null);
        fl_left = (FrameLayout) view.findViewById(R.id.fl_left);
        return view;
    }
}
