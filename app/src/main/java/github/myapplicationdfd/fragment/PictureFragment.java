package github.myapplicationdfd.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import github.myapplicationdfd.CommonConifg;
import github.myapplicationdfd.Entity.NewsTabBean;
import github.myapplicationdfd.Entity.NewsTitleBean;
import github.myapplicationdfd.R;
import github.myapplicationdfd.pager.BasePager;
import github.myapplicationdfd.pager.NewsTabPager;
import github.myapplicationdfd.pager.PagerCreater;
import github.myapplicationdfd.pager.PagerManageUtil;
import github.myapplicationdfd.protocal.BaseProtocal;
import github.myapplicationdfd.utils.CommonUtils;
import github.myapplicationdfd.utils.Json2bean;
import github.myapplicationdfd.view.XViewPager;

/**
 * Created by PC on 2016/8/26.
 *
 *
 * initView-->getTarView--->initDate
 */
public class PictureFragment extends BaseFragment{

    private LinearLayout content;
    private PagerManageUtil pagerManageUtil;

    @Override
    protected View getTarView() {
        return content;
    }

    @Override
    public void initDate() {
        showLoading("loading...");
//        String  uri=CommonConifg.news_title_tab;
//        getNetWorkData(uri);
        getNetWorkData();
    }
    private XViewPager xvp;
    private RecyclerTabLayout tabLayout;
    @Override
    protected View  initView(){
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.framgment_pager,null);
        content = (LinearLayout) inflate.findViewById(R.id.pager_content);
        xvp = (XViewPager) inflate.findViewById(R.id.xvp);
        tabLayout = (RecyclerTabLayout) inflate.findViewById(R.id.tablayout);
        pagerManageUtil = new PagerManageUtil();
        return  inflate;
    }



    public  void  getNetWorkData(){

        ArrayList<NewsTitleBean> list=new ArrayList<>();
        String[] names = mActivity.getResources().getStringArray(R.array.picture_category_list_names);
        String[] ids = mActivity.getResources().getStringArray(R.array.picture_category_list_ids);
        LogUtil.e("ids==>"+ids[0]);
        NewsTitleBean bean=null;
        for (int i=0;i<names.length;i++) {
            LogUtil.e("ids param==>"+ids[i]);
            bean=new NewsTitleBean();
//            bean.setCode(ids[i]+"");
            bean.setCode((i+1)+"");
            bean.setName(names[i]);
            list.add(bean);
        }

        MyAdapter adapter=new MyAdapter(list);
        xvp.setOffscreenPageLimit(list.size());
        xvp.setAdapter(adapter);
        tabLayout.setUpWithViewPager(xvp);
        showSuccessView();


    }



    public  class  MyAdapter extends PagerAdapter{
        private  ArrayList<NewsTitleBean> list;
        public MyAdapter(ArrayList<NewsTitleBean> list) {
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(View)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            TextView tv=new TextView(mActivity);
//            tv.setText(list.get(position).getName()+"=="+list.get(position).getChannelId());
//            container.addView(tv);
            if(pagerManageUtil==null){
                pagerManageUtil=new PagerManageUtil();
            }
            BasePager pager = pagerManageUtil.get(list.get(position).getCode());
            LogUtil.e("list===>"+list);
            if(pager==null) {
                pager = PagerCreater.getInstance().createPicturePager(mActivity, list.get(position).getCode());
                pager.initData();
                pagerManageUtil.put(list.get(position).getCode(),pager);
            }
            container.addView(pager.getView());
            return  pager.getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName();
        }
    }


}
