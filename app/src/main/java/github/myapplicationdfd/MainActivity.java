package github.myapplicationdfd;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import github.myapplicationdfd.Entity.LeftListBean;
import github.myapplicationdfd.Entity.LeftListManage;
import github.myapplicationdfd.activity.base.BaseActivity;
import github.myapplicationdfd.activity.base.BaseAppCompateActivity;
import github.myapplicationdfd.fragment.BaseFragment;
import github.myapplicationdfd.fragment.FragmentManageUtil;
import github.myapplicationdfd.fragment.NewsFragment;
import github.myapplicationdfd.fragment.PictureFragment;

public class MainActivity extends BaseActivity {


    private ListView left_listview;
    private  int currentItem=0;
    private FragmentTransaction transaction;

    @Override

    protected void initEvents() {
        super.initEvents();
        LeftListManage manage=new LeftListManage();
        final FragmentManageUtil fragmentManage=new FragmentManageUtil();


        View inflate = mActivity.getLayoutInflater().inflate(R.layout.left_content_layout, null);
        left_listview = (ListView) inflate.findViewById(R.id.left_listview);

        final ArrayList<LeftListBean> leftData = manage.getData();
        final LeftListViewAdapter adapter=new LeftListViewAdapter(leftData);
        left_listview.setAdapter(adapter);
        fl_left.addView(inflate);
        left_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentItem=position;
                adapter.notifyDataSetChanged();

                transaction = getSupportFragmentManager().beginTransaction();
                LeftListBean bean = leftData.get(position);
                BaseFragment fragment = fragmentManage.get(bean.getName());
                if(fragment==null) {
                    if (position == 0) {
                        fragment = new NewsFragment();
                    } else if (position == 1) {
                        fragment = new PictureFragment();
                    }
                    fragmentManage.set(bean.getName(), fragment);
                }
                setToolBarTile(leftData.get(position).getName());
                transaction.replace(R.id.fl_content, fragment);
                transaction.commit();
                CloseLeft();
            }
        });

        transaction = getSupportFragmentManager().beginTransaction();
        NewsFragment fragment = new NewsFragment();
        fragmentManage.set(leftData.get(0).getName(),fragment);
        setToolBarTile(leftData.get(0).getName());
        transaction.replace(R.id.fl_content,fragment);
        transaction.commit();
    }


    /**
     * 菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int  itemid=item.getItemId();
       switch (itemid){
           case R.id.action_capture:

               break;
           case R.id.action_about_us:
               break;

           case R.id.action_feedback:
               Toast.makeText(MainActivity.this,"33333",Toast.LENGTH_SHORT).show();
               break;


       }
        return true;
    }






    private  class  LeftListViewAdapter extends BaseAdapter{
        private  ArrayList<LeftListBean> list;
        public LeftListViewAdapter(ArrayList<LeftListBean> list){
            this.list=list;
        }
        @Override
        public int getCount() {
            return (list==null)?0:list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mActivity.getLayoutInflater().inflate(R.layout.left_list_item,null);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        ImageView iv= (ImageView) view.findViewById(R.id.iv_news);
        LeftListBean bean = list.get(position);
        tv_name.setText(bean.getName());
        iv.setImageResource(bean.getIcon());
        if(currentItem==position){
            tv_name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        }else{
            tv_name.setTextColor(mActivity.getResources().getColor(R.color.black));
        }

        return  view;
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

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    public boolean isApplyKitKatTranslucency() {
        return true;
    }
}
