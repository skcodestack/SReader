package github.myapplicationdfd.activity.base;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import github.myapplicationdfd.R;
import github.myapplicationdfd.fragment.ItemFragment;
import github.myapplicationdfd.fragment.dummy.DummyContent;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

/**
 * Author root
 * Date: 2016/8/29.
 */
public  class NewBaseActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener,ItemFragment.OnListFragmentInteractionListener {

    public  LinearLayout ll_left;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private ActionBarDrawerToggle toggle;
    private ViewAnimator viewAnimator;
    private ArrayList<SlideMenuItem> list=new ArrayList<>();
    private ItemFragment contentFragment;

    private Context mContext;
    protected void setToolBarTile(String title){
        if(toolbar!=null) {
            toolbar.setTitle(title);
        }
    }

    protected  void  CloseLeft(){
        if(drawer_layout!=null) {
            drawer_layout.closeDrawer(ll_left);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;


        setContentView(R.layout.activity_main1);
        ll_left = (LinearLayout) findViewById(R.id.left_drawer);
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.common_toolbar);
        toolbar.setTitle("新闻快递");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        contentFragment = ItemFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_content, contentFragment)
                .commit();



        //drawer
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ll_left.removeAllViews();
                ll_left.invalidate();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && ll_left.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }
        };
        toggle.syncState();


        drawer_layout.setDrawerListener(toggle);
        drawer_layout.setScrimColor(Color.TRANSPARENT);

        createMenuList();



        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawer_layout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem("meun01", R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem("meun02", R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem("meun03", R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem("meun04", R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem("meun05", R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem("meun06", R.drawable.icn_5);
        list.add(menuItem5);

    }


    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawer_layout.closeDrawers();

    }
    @Override
    public void addViewToContainer(View view) {
        drawer_layout.addView(view);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case "meun01":
                return screenShotable;
            default:
                return replaceFragment(screenShotable, position);
        }
    }
    ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition){
        View view = findViewById(R.id.fl_content);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        ItemFragment contentFragment=ItemFragment.newInstance(2);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, contentFragment).commit();
        return contentFragment;
    }



    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(mContext,"this is :"+item.content,Toast.LENGTH_SHORT).show();
    }
}
